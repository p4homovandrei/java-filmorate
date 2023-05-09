package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    HashMap<Integer, Film> dataFilms = new HashMap<>();

    Integer id = 0;

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма " + film.getName());
        ++id;
        film.setId(id);
        dataFilms.put(id, film);
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма " + film.getName());
        if (dataFilms.containsKey(film.getId())) {
            dataFilms.put(film.getId(), film);
        return film;
        }
        else throw new RuntimeException();
    }

    @GetMapping("/films")
    public ArrayList<Film> getAllFilms() {
        log.info("Получен запрос на получение списка фильмов.");
        return new ArrayList<>(dataFilms.values());
    }
}
