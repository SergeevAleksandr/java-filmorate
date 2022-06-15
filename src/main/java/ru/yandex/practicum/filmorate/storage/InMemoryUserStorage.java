package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
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
        check(user);
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

    public User put(User user) {
        check(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Нет пользователя с таким ключём");
        }
        users.put(user.getId(), user);
        log.debug("Обновлен пользователь {}", user.getName());
        return user;
    }
    public void check(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Адрес электронной почты не может быть пустым и должен быть в правильном формате.");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

    public User findById(Long id) throws UserNotFoundException {

        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь с таким Id" + id + " не найден");
        }

        return users.get(id);
    }
}
