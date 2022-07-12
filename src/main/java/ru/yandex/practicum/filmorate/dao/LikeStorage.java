package ru.yandex.practicum.filmorate.dao;

import java.util.Set;

public interface LikeStorage {

    Long addLike(Long filmId, Long userId);

    Set<Long> findAllLikesFilm(Long filmId);

    Long deleteLike(Long filmId, Long userId);

    Long deleteAllLikesForFilm(Long filmId);

    Long deleteAllLikesForUser(Long userId);
}
