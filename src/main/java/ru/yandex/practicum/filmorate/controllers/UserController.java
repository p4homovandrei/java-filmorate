package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addUsersFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.addUsersFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<Optional<User>> getAllFriendUser(@PathVariable String id) {
        return userService.getUsersFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<Optional<User>> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriend(id, otherId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteUsersFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.deleteUsersFriend(id, friendId);
    }


    @GetMapping("/users")
    public List<Optional<User>> getAllUser() {
        return userService.getAllUsers();
    }

    @PutMapping("/users")
    public Optional<User> putUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public Optional<User> postUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handle(final NoFoundDataException e) {
        return Map.of("error", "Ошибка с параметром count.", "errorMessage", e.getMessage());
    }
}
