package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validate.Annotation.MinimumDate;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data

public class Film {

    Integer id;

    @NotBlank
    String name;

    @Size(max = 200,message = "Описание свыше 200 символов")
    String description;

    @MinimumDate
    LocalDate releaseDate;

    @Positive
    Long duration;
}
