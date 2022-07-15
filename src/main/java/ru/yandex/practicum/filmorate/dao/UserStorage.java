package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.List;

public interface UserStorage {
    User findByID(long userID) throws ObjectNotFoundException;

    List<User> findAll() throws ObjectNotFoundException;

    User create(User user);

    User update(User user) throws ObjectNotFoundException;

    Long delete(Long userId);

    Long addFriend(Long userId, Long friendId);

    Collection findUserFriendsById(Long userId);

    Collection findUserFriendsIdById(Long userId);

    Long deleteFriend(Long userId, Long friendId);

    Collection<User> findCommonFriends(Long firstUserId, Long secondUserId);
}
