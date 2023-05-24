package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    HashMap<Integer, Film> dataFilms = new HashMap<>();

    Integer id = 0;
    @Override
    public Film saveFilm(Film film) {
        ++id;
        film.setId(id);
        dataFilms.put(id, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (dataFilms.containsKey(film.getId())) {
            dataFilms.put(film.getId(), film);
            return film;
        } else {
            throw new RuntimeException();//Поменять!
        }
    }

    @Override
    public Film deleteFilm(Integer Id) {
        return null;
    }

    @Override
    public Film getFilm(Integer id) {
        if (dataFilms.containsKey(id)) {
            return dataFilms.get(id);
        }
        else throw new NoFoundDataException("Фильм не найден.");
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(dataFilms.values());
    }
}
