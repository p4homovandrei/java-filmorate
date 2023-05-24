package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;
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

    @GetMapping("/films/{id}")
    public Optional<Film> getFilm(@PathVariable String id) {
        return filmService.getFilm(id);
    }

    @PostMapping("/films")
    public Optional<Film> postFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Optional<Film> putFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
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

    @GetMapping("/films/popular")
    public List<Optional<Film>> getPopularFilm(@RequestParam(required = false, defaultValue = "10") String count) {
        return filmService.popularFilm(count);
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMPA(@PathVariable String id) {
        return filmService.getMPA(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMPA() {
        return filmService.getAllMPA();
    }

    @GetMapping("/genres/{id}")
    public Genre getAllGenre(@PathVariable String id) {
        return filmService.getGenre(id);
    }

    @GetMapping("/genres")
    public List<Genre> getGenre() {
        return filmService.getAllGenre();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handle(final NoFoundDataException e) {
        return Map.of("error", "Данные не найдены.", "errorMessage", e.getMessage());
    }

}
