package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class FilmController {

    @Autowired
    FilmService filmService;

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
    public void likeFilm(@PathVariable String id,@PathVariable String userId){
        filmService.likeFilm(id,userId);
    }
    @DeleteMapping("/films/{id}/like/{userId}")
    public void unLikeFilm(@PathVariable String id,@PathVariable String userId){
        filmService.unLikeFilm(id,userId);
    }
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable String id){
        return filmService.getFilm(id);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handle(final NoFoundDataException e) {
        return Map.of(
                "error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }
}
