package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final UserDbStorage  userDbStorage;

    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }
    public Collection<User> findAll() throws ObjectNotFoundException {
        return userDbStorage.findAll();
    }
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userDbStorage.create(user);
    }
    public User update(User user) throws UserNotFoundException, ObjectNotFoundException {
        return userDbStorage.update(user);
    }
    public User findById(Long userID) throws ObjectNotFoundException {
        return userDbStorage.findByID(userID);
    }
    public Long addFriend(Long userId, Long friendId) throws ObjectNotFoundException {

        userDbStorage.findByID(userId);
        userDbStorage.findByID(friendId);
        return userDbStorage.addFriend(userId, friendId);
    }
    public List<User> findAllUserFriendsById(Long userId) throws ObjectNotFoundException {
        userDbStorage.findByID(userId);
        return (List<User>) userDbStorage.findUserFriendsById(userId);
    }
    public void deleteFriend(Long userId, Long friendId) throws  ObjectNotFoundException {

        userDbStorage.findByID(userId);
        userDbStorage.findByID(friendId);
        Collection userFriends = userDbStorage.findUserFriendsIdById(userId);
        if (!userFriends.contains(friendId))
                {
            throw new ObjectNotFoundException( userId + ", не друг пользователю " + friendId);
        }
        userDbStorage.deleteFriend(userId,friendId);
        log.debug(userId +" удален из друзей "+ friendId);
    }
     public List<User> findCommonFriends(Long firstUserId, Long secondUserId) throws ObjectNotFoundException {
       userDbStorage.findByID(firstUserId);
       userDbStorage.findByID(secondUserId);
       return (List<User>) userDbStorage.findCommonFriends(firstUserId,secondUserId);
    }
}
