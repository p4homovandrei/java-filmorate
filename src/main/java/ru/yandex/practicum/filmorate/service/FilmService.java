package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmService {
    @Autowired
    FilmStorage fstorage;
    @Autowired
    UserService ustorage;
    public Film addFilm (Film film){
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
        film.getIdUsersLike().add(Long.valueOf(userId));
    }
    public void unLikeFilm(String id , String userId){
        ustorage.getUser(userId);
        Film film = fstorage.getFilm(Integer.valueOf(id));
        film.getIdUsersLike().remove(Long.valueOf(userId));
    }

    public Film getFilm (String id){
       return fstorage.getFilm(Integer.valueOf(id));
    }
  /*  public List<Film> getMostPopularFilm(String count){

    }*/
}
