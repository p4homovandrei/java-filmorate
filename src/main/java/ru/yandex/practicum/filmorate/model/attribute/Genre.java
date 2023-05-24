package ru.yandex.practicum.filmorate.model.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    Integer id;
    String name;

    public Genre(int id) {
        this.id = Integer.valueOf(id);
        switch (this.id) {
            case 1:
                name = "Комедия";
                break;
            case 2:
                name = "Драма";
                break;
            case 3:
                name = "Мультфильм";
                break;
            case 4:
                name = "Триллер";
                break;
            case 5:
                name = "Документальный";
                break;
            case 6:
                name = "Боевик";
                break;
        }
    }

/*COMEDY,
    DRAMA,
    CARTOON,
    TRILLER,
    DOC,
    ACTION*/
}
