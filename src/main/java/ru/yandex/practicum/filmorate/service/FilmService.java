package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    @Autowired
    FilmStorage fstorage;
    @Autowired
    UserService ustorage;

    public Film addFilm(Film film) {
        Film rfilm = fstorage.saveFilm(film);
        return rfilm;
    }

    public Film updateFilm(Film film) {
        Film rfilm = fstorage.updateFilm(film);
        return rfilm;
    }

    public List<Film> getAllFilms() {
        return fstorage.getAllFilms();
    }

    public void likeFilm(String id, String userId) {
        ustorage.getUser(userId);
        Film film = fstorage.getFilm(Integer.valueOf(id));
        film.getIdUsersLike().add(Integer.valueOf(userId));
    }

    public void unLikeFilm(String id, String userId) {
        ustorage.getUser(userId);
        Film film = fstorage.getFilm(Integer.valueOf(id));
        film.getIdUsersLike().remove(Integer.valueOf(userId));
    }

    public Film getFilm(String id) {
        return fstorage.getFilm(Integer.valueOf(id));
    }

    public Set<Film> popularFilm(String count) {
        return fstorage.getPopularFilms(count);
    }
}
