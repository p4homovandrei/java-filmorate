package ru.yandex.practicum.filmorate.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;


@RestController
public class FilmController {


    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        Film rFilm = filmService.addFilm(film);
        return rFilm;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) {
        Film rFilm = filmService.updateFilm(film);
        return rFilm;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable String id, @PathVariable String userId) {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void unLikeFilm(@PathVariable String id, @PathVariable String userId) {
        filmService.unLikeFilm(id, userId);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable String id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/films/popular")
    public Set<Film> getPopularFilm(@RequestParam(required = false, defaultValue = "10") String count) {
        return filmService.popularFilm(count);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handle(final NoFoundDataException e) {
        return Map.of("error", "Данные не найдены.", "errorMessage", e.getMessage());
    }
}
