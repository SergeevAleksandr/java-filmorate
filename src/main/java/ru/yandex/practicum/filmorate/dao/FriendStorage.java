package ru.yandex.practicum.filmorate.dao;


import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendStorage {
    Long addFriend(Long userId, Long friendId);

    Long deleteFriend(Long userId, Long friendId);

    Collection<Long> findUserFriendsIdById(Long userId);

    Collection<User> findUserFriendsById(Long userId);

    Collection<User> findCommonFriends(Long firstUserId, Long secondUserId);
}
