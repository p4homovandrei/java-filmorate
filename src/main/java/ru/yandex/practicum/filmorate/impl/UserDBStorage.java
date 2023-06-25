package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.util.*;

@Component
public class UserDBStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> saveUser(User user) {
        String sqlQuery = "INSERT INTO USERS (email,login,name,birthday)\n" +
                "VALUES (?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        return getUser(keyHolder.getKey().intValue());
    }

    @Override
    public Optional<User> updateUser(User user) {
        String id = String.valueOf(user.getId());
        jdbcTemplate.update("UPDATE USERS SET email = ?,login = ?,name = ?, birthday = ?\n" +
                "where id = ?;", user.getEmail(), user.getLogin(), user.getName(),
                java.sql.Date.valueOf(user.getBirthday()),id);

        return getUser(user.getId());
    }

    @Override
    public Optional<User> getUser(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from Users where id = ?", id);

        if (userRows.next()) {
            User user = new User(userRows.getInt("ID"), userRows.getString("EMAIL"),
                    userRows.getString("LOGIN"), userRows.getString("NAME"),
                    userRows.getDate("BIRTHDAY").toLocalDate());
            return Optional.of(user);
        }
        throw new NoFoundDataException("Пользователь с id =" + id + " не найден");
    }

    @Override
    public List<Optional<User>> getAllUsers() {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from Users;");
        List<Optional<User>> list = new ArrayList<>();
        while (userRows.next()) {
            User user = new User(userRows.getInt("ID"), userRows.getString("EMAIL"),
                    userRows.getString("LOGIN"), userRows.getString("NAME"),
                    userRows.getDate("BIRTHDAY").toLocalDate());
            list.add(Optional.of(user));
        }
        return list;
    }

    @Override
    public void addFriend(String userId, String friendId) {
        jdbcTemplate.update("INSERT INTO FRIENDS (ID_USER1 , ID_USER2 ,STATUS_1) " +
                        "VALUES (?,?,?);",
                userId, friendId, true);
    }

    @Override
    public List<Optional<User>> getUserFriends(String id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select ID_USER2 from FRIENDS\n" +
                "where ID_USER1 = ? AND STATUS_1 =? ", id,true);
        List<Optional<User>> list = new LinkedList<Optional<User>>();
        while (userRows.next()) {
            list.add(getUser(userRows.getInt("ID_USER2")));
        }
        return list;
    }

    @Override
    public void deleteFriend(String userId, String friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS " +
                        "WHERE ID_USER1 = ? AND ID_USER2 = ? ;",
                        userId, friendId);
    }

}
