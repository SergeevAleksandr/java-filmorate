package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmStorage userStorage;

    public FilmService(FilmStorage filmDbStorage, FilmStorage userDbStorage) {
        this.filmStorage = filmDbStorage;
        this.userStorage = userDbStorage;
    }
    public Collection<Film> findAll() throws ObjectNotFoundException {
        return filmStorage.findAll();
    }
    public Film getFilmById(Long id) throws ObjectNotFoundException {
        return filmStorage.findByID(id);
    }
    public Film create(Film film) {
        return filmStorage.create(film);
    }
    public Film update(Film film) throws  ObjectNotFoundException {
        return filmStorage.update(film);
    }
    public Collection<Film> findPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }
    public Long deleteLike(Long filmId, Long userId) throws ObjectNotFoundException {
        filmStorage.findByID(filmId);
        userStorage.findByID(userId);
        return filmStorage.deleteLike(filmId,userId);
    }
    public Long addLike(Long filmId, Long userId) throws ObjectNotFoundException {
        filmStorage.findByID(filmId);
        userStorage.findByID(userId);
        return filmStorage.addLike(filmId,userId);
    }
}
