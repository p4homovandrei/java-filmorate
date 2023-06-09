package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {
    private Integer id;
    @Email
    private String email;
    @NotBlank
    private String login;

    private String name;
    @PastOrPresent
    private LocalDate birthday;

    private Set<Long> idFriends = new HashSet<>();

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name.isBlank()) this.name = login;
        else this.name = name;
        this.birthday = birthday;
    }
}
