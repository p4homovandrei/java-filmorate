package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    FilmStorage filmDB;

    UserService userDB;

    @Autowired
    public FilmService(FilmStorage filmDB, UserService userDB) {
        this.filmDB = filmDB;
        this.userDB = userDB;
    }

    public Optional<Film> addFilm(Film film) {
        return filmDB.saveFilm(film);
    }

    public Optional<Film> updateFilm(Film film) {
        return filmDB.updateFilm(film);

    }

    public List<Film> getAllFilms() {
        return filmDB.getAllFilms();
    }

    public void likeFilm(String id, String userId) {
        userDB.getUser(userId);
        filmDB.likeFilm(id, userId);
    }

    public void unLikeFilm(String id, String userId) {
        userDB.getUser(userId);
        filmDB.unlikeFilm(id, userId);
    }

    public Optional<Film> getFilm(String id) {
        return filmDB.getFilm(Integer.valueOf(id));
    }

    public List<Optional<Film>> popularFilm(String count) {
        return filmDB.getPopularFilms(count);
    }

    public Mpa getMPA(String id) {
        return filmDB.getMpa(id);
    }

    public List<Mpa> getAllMPA() {
        return filmDB.getAllMpa();
    }

    public Genre getGenre(String id) {
        return filmDB.getGenre(id);
    }

    public List<Genre> getAllGenre() {
        return filmDB.getAllGenre();
    }
}
