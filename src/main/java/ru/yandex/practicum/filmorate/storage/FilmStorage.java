package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> saveFilm(Film film);

    Optional<Film> updateFilm(Film film);

    Optional<Film> getFilm(Integer id);

    List<Film> getAllFilms();

    List<Optional<Film>> getPopularFilms(String count);

    void likeFilm(String filmId, String userId);

    void unlikeFilm(String filmId, String userId);

    Mpa getMpa(String id);

    List<Mpa> getAllMpa();

    Genre getGenre(String id);

    List<Genre> getAllGenre();
}
