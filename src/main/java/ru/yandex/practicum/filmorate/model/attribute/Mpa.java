package ru.yandex.practicum.filmorate.model.attribute;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {
    int id;
    String name;

    public Mpa(int id) {
        this.id = id;
        switch (id) {
            case 1:
                name = "G";
                break;
            case 2:
                name = "PG";
                break;
            case 3:
                name = "PG-13";
                break;
            case 4:
                name = "R";
                break;
            case 5:
                name = "NC-17";
                break;
        }
    }
/* G,
    PG,
    PG13,
    R,
    NC17*/
}
