package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(Integer id);

    List<Film> getAllFilms();

    Set<Film> getPopularFilms(String count);
}
