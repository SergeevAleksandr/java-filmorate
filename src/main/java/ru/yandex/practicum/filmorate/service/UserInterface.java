package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserInterface {
    Collection<User> findAll();

    User create(User user);

    User put(User user) throws UserNotFoundException;
    User findById(Long id);
    void addFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId) throws UserNotFoundException;

    List<User> findAllFriendsUserById(Long id);

    List<User> findCommonFriends(Long id, Long otherId);

    void isExistById(Long id) throws UserNotFoundException;
}
