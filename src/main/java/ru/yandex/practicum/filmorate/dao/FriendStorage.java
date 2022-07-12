package ru.yandex.practicum.filmorate.dao;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;

public interface FriendStorage {
    Long addFriend(Long userId, Long friendId);

    Long deleteFriend(Long userId, Long friendId);

    Collection<Long> findUserFriendsIdById(Long userId);

    Collection<User> findUserFriendsById(Long userId);

    Collection<User> findCommonFriends(Long firstUserId, Long secondUserId);
}
