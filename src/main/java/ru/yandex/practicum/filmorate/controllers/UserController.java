package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public Collection<User> findAll() throws ObjectNotFoundException {
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public User findById( @PathVariable("id") Long id) throws UserNotFoundException, ObjectNotFoundException {
        return userService.findById(id);
    }
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws UserNotFoundException, ObjectNotFoundException {
        return userService.update(user);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle(final UserNotFoundException e) {
        return Map.of(
                "error", "UserNotFoundException",
                "errorMessage", e.getMessage()
        );
    }
}
