package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User saveUser (User user);
    User updateUser(User user);
    User deleteUser(Integer id);
    User getUser(Integer id);

    List<User> getAllUsers();

    boolean containsUser(Integer id);
}
