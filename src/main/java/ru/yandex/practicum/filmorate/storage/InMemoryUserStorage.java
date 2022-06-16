package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Long, User> users = new HashMap<>();
    protected long id;

    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        for (Long i:users.keySet()){
            if (users.get(i).getEmail().equals(user.getEmail())){
                throw new ValidationException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
        }
        user.setId(++id);
        users.put(user.getId(), user);
        log.debug("Добавлен пользователь {}", user.getName());
        return user;
    }

    public User put(User user) throws UserNotFoundException {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("Пользователь с таким Id " + id + " не найден");
        }
        users.put(user.getId(), user);
        log.debug("Обновлен пользователь {}", user.getName());
        return user;
    }

    public User findById(Long id) throws UserNotFoundException {
        return users.get(id);
    }
    public void isExistById(Long id) throws UserNotFoundException {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь с таким Id " + id + " не найден");
        }
    }
}
