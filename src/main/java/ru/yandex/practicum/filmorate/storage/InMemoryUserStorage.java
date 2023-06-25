package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Integer, User> dataUsers = new HashMap<>();
    private static Integer id = 0;

    @Override
    public User saveUser(User user) {
        ++id;
        user.setId(id);
        dataUsers.put(id, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (dataUsers.containsKey(user.getId())) {
            dataUsers.put(user.getId(), user);
            return user;
        } else throw new RuntimeException();
    }


    @Override
    public User getUser(Integer id) {
        if (dataUsers.containsKey(id)) {
            return dataUsers.get(id);
        } else throw new NoFoundDataException("Пользователь не найден.");
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(dataUsers.values());
    }

    @Override
    public boolean containsUser(Integer id) {
        if (dataUsers.containsKey(id)) {
            return true;
        } else return false;
    }
}
