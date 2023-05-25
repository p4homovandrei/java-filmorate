package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    Map<Integer, Film> dataFilms = new TreeMap<>();


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
    public Film getFilm(Integer id) {
        if (dataFilms.containsKey(id)) {
            return dataFilms.get(id);
        } else throw new NoFoundDataException("Фильм не найден.");
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(dataFilms.values());
    }


    @Override
    public Set<Film> getPopularFilms(String count) {
        Integer i = 0;
        Integer j = Integer.valueOf(count);
        Set<Film> allfilmssorted = new TreeSet<>(dataFilms.values());
        Set<Film> sizedfilms = new TreeSet<>();
        for (Film film : allfilmssorted) {
            if (j.equals(i)) {
                break;
            }
            sizedfilms.add(film);
            ++i;
        }
        return sizedfilms;
    }
}
