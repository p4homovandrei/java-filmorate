package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FilmStorage {
  Film saveFilm (Film film);
  Film updateFilm(Film film);
  Film deleteFilm(Integer id);
  Film getFilm(Integer id);
  List<Film> getAllFilms ();
}
