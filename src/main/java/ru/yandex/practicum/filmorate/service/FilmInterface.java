package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FilmInterface {
    Collection<Film> findAll();

    Film getFilmById(Long id);

    Film create(Film film);

    Film put(Film film) throws FilmNotFoundException;
    List<Long> addLike(Long filmId, Long userId);

    List<Long> deleteLike(Long filmId, Long userId) throws UserNotFoundException;
    public Set<Film> findPopularFilms(Integer count);
    public void isExistById(Long id) throws FilmNotFoundException;
}
