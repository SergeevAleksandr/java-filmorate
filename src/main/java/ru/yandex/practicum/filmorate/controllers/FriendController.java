package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("users/{id}")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final UserService userService;

    @PutMapping("/friends/{friendId}")
    public void addInFriends(
            @PathVariable("id") Long userId,
            @PathVariable("friendId") Long friendId
    ) throws ObjectNotFoundException {
        log.debug("Запрос на добавление в друзья с id {} и id {}",
                userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/friends/{friendId}")
    public void removeFromFriends(
            @PathVariable("id") Long id,
            @PathVariable("friendId") Long friendId
    ) throws UserNotFoundException, ObjectNotFoundException {
        log.debug("Запрос на удаление из друзей id {} и id {}",
                id, friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/friends")
    public Collection<User> findAllFriendsById(@PathVariable("id") Long UserID) throws ObjectNotFoundException {
        log.debug("Запрос на получение друзей id {}", UserID);
        return userService.findAllUserFriendsById(UserID);
    }

    @GetMapping("/friends/common/{otherId}")
    public List<User> findCommonFriends(
            @PathVariable("id") Long id,
            @PathVariable("otherId") Long otherId
    ) throws ObjectNotFoundException {
        log.debug("Запрос на получение общих друзей id {} и id {}", id, otherId);
        return userService.findCommonFriends(id, otherId);
    }

}