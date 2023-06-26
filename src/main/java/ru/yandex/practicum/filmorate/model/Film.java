package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;
import ru.yandex.practicum.filmorate.validate.Annotation.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Film implements Comparable {

    private Integer id;
    @NotBlank
    private String name;

    @Size(max = 200, message = "Описание свыше 200 символов")
    private String description;

    @MinimumDate
    private LocalDate releaseDate;

    @Positive
    private long duration;

    private List<Integer> idUsersLike = new LinkedList<>();

    private List<Genre> genres;

    private Mpa mpa;

    private int rate;

    public Film(Integer id, String name, String description, LocalDate releaseDate, long duration, List<Genre> genres, Mpa mpa, int rate, List<Integer> idUsersLike) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
        this.rate = rate;
        this.idUsersLike = idUsersLike;
    }

    @Override
    public int compareTo(Object o) {
        Film film = (Film) o;
        int result = film.getIdUsersLike().size() - this.getIdUsersLike().size();
        if (result == 0) {
            result = this.id.compareTo(film.id);
        }
        return result;
    }
}