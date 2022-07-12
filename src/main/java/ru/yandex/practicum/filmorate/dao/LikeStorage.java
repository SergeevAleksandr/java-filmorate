package ru.yandex.practicum.filmorate.dao;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

public interface LikeStorage {

    Long addLike(Long filmId, Long userId);

    Set<Long> findAllLikesFilm(Long filmId);

    Long deleteLike(Long filmId, Long userId);

    Long deleteAllLikesForFilm(Long filmId);

    Long deleteAllLikesForUser(Long userId);
}
