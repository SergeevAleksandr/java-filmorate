package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.List;

@RestController
@RequestMapping("users/{id}")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final UserService userService;

    @PutMapping("/friends/{friendId}")
    public void addInFriends(
            @PathVariable("id") Long id,
            @PathVariable("friendId") Long friendId
    ) throws UserNotFoundException {
        log.debug("Получен запрос PUT на добавление в друзья пользователей с id {} и id {}",
                id, friendId);
        userService.isExistById(id);
        userService.isExistById(friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/friends/{friendId}")
    public void removeFromFriends(
            @PathVariable("id") Long id,
            @PathVariable("friendId") Long friendId
    ) throws UserNotFoundException {
        log.debug("Получен запрос DELETE на удаление из друзей пользователей с id {} и id {}",
                id, friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/friends")
    public List<User> findAllFriendsById(@PathVariable("id") Long id) {
        log.debug("Получен запрос GET на получение друзей пользователя с id {}", id);
        return userService.findAllFriendsUserById(id);
    }

    @GetMapping("/friends/common/{otherId}")
    public List<User> findCommonFriends(
            @PathVariable("id") Long id,
            @PathVariable("otherId") Long otherId
    ) {
        log.debug("Получен запрос GET на получение общих друзей пользователя с id {} и id {}", id, otherId);
        return userService.findCommonFriends(id, otherId);
    }
}