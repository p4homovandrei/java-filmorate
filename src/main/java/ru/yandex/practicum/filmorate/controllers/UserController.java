package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UserController {
    HashMap<Integer, User> dataUsers = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    Integer id = 0;
    @PostMapping("/users")
    public User postUser(@Valid  @RequestBody User user) {
        log.info("Получен запрос добавление пользователя " + user.getName());
        ++id;
        if (user.getName() == null)
            user.setName(user.getLogin());
        user.setId(id);
        dataUsers.put(id,user);
        return user;
    }
    @PutMapping("/users")
    public User putUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя " + user.getName());
        if (dataUsers.containsKey(user.getId())) {
            dataUsers.put(user.getId(), user);
            return user;
        }
        else throw new RuntimeException();
    }
    @GetMapping("/users")
    public ArrayList<User> getAllUser ()
    {
        log.info("Получен запрос на получение списка всех пользователей.");
        return new ArrayList<>(dataUsers.values());
    }
}
