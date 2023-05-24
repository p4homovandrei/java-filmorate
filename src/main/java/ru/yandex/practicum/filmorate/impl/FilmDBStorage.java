package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.List;
import java.sql.Date;

@Component
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Film> saveFilm(Film film) {
        String sqlQuery = "insert into FILMS (name,description,duration,release_date,rate,mpa) " + "VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        java.sql.Date date = Date.valueOf(film.getReleaseDate());
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setLong(3, film.getDuration());
            stmt.setDate(4, date);
            stmt.setInt(5, film.getRate());
            stmt.setInt(6, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        if (film.getGenres() != null) setGenreFilms(keyHolder.getKey().intValue(), film.getGenres());
        return getFilm(keyHolder.getKey().intValue());
    }


    @Override
    public Optional<Film> updateFilm(Film film) {
        String id = String.valueOf(film.getId());
        java.sql.Date date = Date.valueOf(film.getReleaseDate());
        jdbcTemplate.update("UPDATE FILMS SET name = ?,description = ?, duration = ?, " + "release_date = ?,rate = ?, mpa = ?" + "where id = ?;", film.getName(), film.getDescription(), film.getDuration(), date, film.getRate(), film.getMpa().getId(), id);
        clearGenreFilmsBeforeUpdate(film.getId());
        if (film.getGenres() != null) setGenreFilms(film.getId(), film.getGenres());
        return getFilm(film.getId());
    }

    @Override
    public Optional<Film> getFilm(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT *  FROM Films as f \n" + "LEFT JOIN MPA AS m ON f.MPA = m.ID \n" + "WHERE f.id = ?", id);
        if (userRows.next()) {
            Film film = new Film(userRows.getInt("ID"), userRows.getString("NAME"), userRows.getString("DESCRIPTION"), userRows.getDate("RELEASE_DATE").toLocalDate(), userRows.getLong("DURATION"), getGenresFilms(String.valueOf(id)), new Mpa(userRows.getInt("MPA")), userRows.getInt("RATE"), getUsersIdLikes(id));
            return Optional.of(film);
        }
        throw new NoFoundDataException("Фильм с id = " + id + " не найден.");
    }

    @Override
    public List<Film> getAllFilms() {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT *  FROM Films as f \n" + "LEFT JOIN MPA AS m ON f.MPA = m.ID \n");
        List<Film> list = new ArrayList<>();
        while (userRows.next()) {
            Integer id = userRows.getInt("ID");
            Film film = new Film(userRows.getInt("ID"), userRows.getString("NAME"), userRows.getString("DESCRIPTION"), userRows.getDate("RELEASE_DATE").toLocalDate(), userRows.getLong("DURATION"), getGenresFilms(String.valueOf(id)), new Mpa(userRows.getInt("MPA")), userRows.getInt("RATE"), getUsersIdLikes(id));
            list.add(film);
        }
        return list;
    }

    private List<Integer> getUsersIdLikes(int id) {
        List<Integer> idLikes = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID_USER FROM FILM_LIKE f \n" + "WHERE f.ID_FILMS = ?;", id);
        while (userRows.next()) {
            idLikes.add(userRows.getInt("ID_USER"));
        }
        return idLikes;
    }

    private List<Genre> getGenresFilms(String filmId) {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT g.ID FROM FILMS f \n" + "LEFT JOIN FILM_GENRE fg ON f.ID = fg.ID_FILMS \n" + "LEFT JOIN GENRE g ON fg.ID_GENRE = g.ID \n" + "WHERE f.ID = ?;", filmId);
        while (userRows.next()) {
            if (userRows.getString("ID") != null) genres.add(new Genre(userRows.getInt("ID")));
        }
        return genres;
    }

    private void setGenreFilms(int id, List<Genre> genres) {
        Set<Genre> s = new LinkedHashSet<>(genres);
        for (Genre genre : s) {
            jdbcTemplate.update("insert into FILM_GENRE (ID_FILMS,ID_GENRE)" + "VALUES (?,?)", id, genre.getId());
        }
    }

    private void clearGenreFilmsBeforeUpdate(int id) {
        jdbcTemplate.update("delete from film_genre where ID_FILMS = ?", id);
    }

    @Override
    public List<Optional<Film>> getPopularFilms(String count) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID_FILMS,COUNT(ID_USER) " + " FROM FILM_LIKE fl GROUP BY ID_FILMS " + " ORDER BY COUNT(ID_USER) DESC" + " LIMIT ?;", count);
        List<Optional<Film>> list = new ArrayList<>();
        while (userRows.next()) {
            list.add(getFilm(userRows.getInt("ID_FILMS")));
        }//
        if (Integer.valueOf(count) - list.size() > 0) {
            Integer x = Integer.valueOf(count) - list.size();
            SqlRowSet u2 = jdbcTemplate.queryForRowSet("SELECT ID " + " FROM FILMS " + " ORDER BY ID ASC" + " LIMIT ?;", x);
            while (u2.next()) {
                list.add(getFilm(u2.getInt("ID")));
            }
        }
        return list;
    }

    @Override
    public void likeFilm(String filmId, String userId) {
        jdbcTemplate.update("insert into FILM_LIKE (ID_FILMS,ID_USER)" + "VALUES (?,?)", filmId, userId);
    }

    @Override
    public void unlikeFilm(String filmId, String userId) {
        jdbcTemplate.update("DELETE FROM PUBLIC.FILM_LIKE " + "WHERE ID_FILMS = ? AND ID_USER=?", filmId, userId);
    }

    @Override
    public Mpa getMpa(String id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID, RATE\n" + "FROM PUBLIC.MPA " + "WHERE ID = ?", id);
        int iid = 0;
        if (userRows.next()) iid = userRows.getInt("ID");
        if (iid == 0) throw new NoFoundDataException("MPA ID = " + id + " не найден");
        return new Mpa(iid);
    }

    @Override
    public List<Mpa> getAllMpa() {
        List<Mpa> list = new LinkedList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID, RATE\n" + "FROM PUBLIC.MPA");
        while (userRows.next()) {
            list.add(new Mpa(userRows.getInt("ID")));
        }
        return list;
    }

    @Override
    public Genre getGenre(String id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID, NAME\n" + "FROM PUBLIC.GENRE " + "WHERE ID = ?", id);
        int iid = 0;
        if (userRows.next()) iid = userRows.getInt("ID");
        if (iid == 0) throw new NoFoundDataException("GENRE ID = " + id + " не найден");
        return new Genre(iid);
    }

    @Override
    public List<Genre> getAllGenre() {
        List<Genre> list = new LinkedList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT ID, NAME\n" + "FROM PUBLIC.GENRE");
        while (userRows.next()) {
            list.add(new Genre(userRows.getInt("ID")));
        }
        return list;
    }
}
