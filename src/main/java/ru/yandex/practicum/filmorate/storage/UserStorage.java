package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    Optional<User> saveUser(User user);

    Optional<User> updateUser(User user);

    Optional<User> getUser(Integer id);

    List<Optional<User>> getAllUsers();

    void addFriend(String userId, String friendId);

    List<Optional<User>> getUserFriends(String id);

    void deleteFriend(String id, String friendId);
}
