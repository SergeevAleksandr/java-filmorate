package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public Collection<User> findAll() throws ObjectNotFoundException {
        return userStorage.findAll();
    }
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }
    public User update(User user) throws UserNotFoundException, ObjectNotFoundException {
        return userStorage.update(user);
    }
    public User findById(Long userID) throws ObjectNotFoundException {
        return userStorage.findByID(userID);
    }
    public Long addFriend(Long userId, Long friendId) throws ObjectNotFoundException {

        userStorage.findByID(userId);
        userStorage.findByID(friendId);
        return userStorage.addFriend(userId, friendId);
    }
    public List<User> findAllUserFriendsById(Long userId) throws ObjectNotFoundException {
        userStorage.findByID(userId);
        return (List<User>) userStorage.findUserFriendsById(userId);
    }
    public void deleteFriend(Long userId, Long friendId) throws  ObjectNotFoundException {

        userStorage.findByID(userId);
        userStorage.findByID(friendId);
        Collection userFriends = userStorage.findUserFriendsIdById(userId);
        if (!userFriends.contains(friendId))
                {
            throw new ObjectNotFoundException( userId + ", не друг пользователю " + friendId);
        }
        userStorage.deleteFriend(userId,friendId);
        log.info(userId +" удален из друзей "+ friendId);
    }
     public List<User> findCommonFriends(Long firstUserId, Long secondUserId) throws ObjectNotFoundException {
         userStorage.findByID(firstUserId);
         userStorage.findByID(secondUserId);
       return (List<User>) userStorage.findCommonFriends(firstUserId,secondUserId);
    }
}
