package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userDB;

    @Autowired
    public UserService(UserStorage userDB) {
        this.userDB = userDB;
    }

    public Optional<User> getUser(String id) {
        return userDB.getUser(Integer.valueOf(id));
    }

    public Optional<User> addUser(User user) {
        if (user.getName().isBlank()) user.setName(user.getLogin());
        return userDB.saveUser(user);
    }

    public Optional<User> updateUser(User user) {
        return userDB.updateUser(user);
    }

    public List<Optional<User>> getAllUsers() {
        return userDB.getAllUsers();
    }

    public void addUsersFriend(String id, String friendId) {
        userDB.getUser(Integer.valueOf(id));
        userDB.getUser(Integer.valueOf(friendId));
        userDB.addFriend(id, friendId);
    }

    public void deleteUsersFriend(String id, String friendId) {
        userDB.deleteFriend(id, friendId);
    }


    public List<Optional<User>> getUsersFriend(String id) {
        return userDB.getUserFriends(id);
    }


    public Set<Optional<User>> getCommonFriend(String id, String otherId) {
        List<Optional<User>> firstUserList = this.getUsersFriend(id);
        List<Optional<User>> secondUserList = this.getUsersFriend(otherId);
        Set<Optional<User>> commonFriends = new HashSet<>();
        for (Optional<User> user : firstUserList) {
            for (Optional<User> otherUser : secondUserList) {
                if (user.get().getId().equals(otherUser.get().getId())) {
                    commonFriends.add(otherUser);
                }
            }
        }
        return commonFriends;
    }
}
