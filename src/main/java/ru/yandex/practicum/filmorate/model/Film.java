package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validate.Annotation.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable {

    private Integer id;
    @NotBlank
    private String name;

    @Size(max = 200, message = "Описание свыше 200 символов")
    private String description;

    @MinimumDate
    private LocalDate releaseDate;

    @Positive
    private Long duration;

    Set<Integer> idUsersLike = new HashSet<>();

    public Film(Integer id, String name, String description, LocalDate releaseDate, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
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
