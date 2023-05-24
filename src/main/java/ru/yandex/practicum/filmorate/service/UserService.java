package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoFoundDataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserStorage storage;

    void addFriend(User user)
    {
        /*storage.getUser()
        storage.saveUser(userasd);*/
    }

    public User addUser(User user) {
        if (user.getName().isBlank())
            user.setName(user.getLogin());
        return storage.saveUser(user);
    }
    public User updateUser(User user){
        return storage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }
    public void addUsersFriend(String id ,String friendId){
        Integer uId = Integer.valueOf(id);
        Integer fId= Integer.valueOf(friendId);
        User user1 = storage.getUser(uId);
        User user2 = storage.getUser(fId);
        user1.getIdFriends().add(Long.valueOf(fId));
        user2.getIdFriends().add(Long.valueOf(uId));
    }

    public void deleteUsersFriend(String id, String friendId) {
        User user1 = storage.getUser(Integer.valueOf(id));
        user1.getIdFriends().remove(Long.valueOf(friendId));
        User user2 = storage.getUser(Integer.valueOf(friendId));
        user2.getIdFriends().remove(Long.valueOf(id));
    }


    public List<User> getAllFriendUsers(String id) {
        List <User> list = new ArrayList<>();
        Set<Long> idfriends = storage.getUser(Integer.valueOf(id)).getIdFriends();
        if (idfriends.isEmpty()){
            return list;
        }
        for(Long iid :idfriends){
            list.add(storage.getUser(Integer.valueOf(String.valueOf(iid))));
        }
        return list;
    }

    public User getUser(String id) {
       return storage.getUser(Integer.valueOf(id));
    }

    public Set<User> getCommonFriend(String id, String otherId) {
        List <User> firstUserList = this.getAllFriendUsers(id);
        List <User> secondUserList = this.getAllFriendUsers(otherId);
        Set <User> commonFriends = new HashSet<>();
        for(User user : firstUserList){
            for(User otherUser: secondUserList){
                if(user.getId().equals(otherUser.getId())){
                    commonFriends.add(otherUser);
                }
            }
        }
        return  commonFriends;
    }
}
