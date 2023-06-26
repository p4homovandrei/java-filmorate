package ru.yandex.practicum.filmorate.model.attribute;


import lombok.Getter;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Friends {
    Set<User> confirmIdFriends;


    public Friends() {
        this.confirmIdFriends = new HashSet<>();
    }


}


/**
 * public class Friend extends User>?
 */