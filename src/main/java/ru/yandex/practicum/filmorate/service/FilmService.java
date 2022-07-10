package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
@Service
public class FilmService implements FilmInterface{
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    public FilmService(FilmDbStorage filmDbStorage, UserDbStorage userDbStorage) {
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
    }


    public Collection<Film> findAll() throws ObjectNotFoundException {
        return filmDbStorage.findAll();
    }

    public Film getFilmById(Long id) throws ObjectNotFoundException {
        return filmDbStorage.findByID(id);
    }

    public Film create(Film film) {
        return filmDbStorage.create(film);
    }

    public Film put(Film film) throws FilmNotFoundException {
        return null;
    }

    public Film update(Film film) throws  ObjectNotFoundException {
        return filmDbStorage.update(film);
    }

    public Collection<Film> findPopularFilms(Integer count) {
        return filmDbStorage.findPopularFilms(count);
    }

    public Long deleteLike(Long filmId, Long userId) throws ObjectNotFoundException {
        filmDbStorage.findByID(filmId);
        userDbStorage.findByID(userId);
        return filmDbStorage.deleteLike(filmId,userId);
    }

    public Long addLike(Long filmId, Long userId) throws ObjectNotFoundException {
        filmDbStorage.findByID(filmId);
        userDbStorage.findByID(userId);
        return filmDbStorage.addLike(filmId,userId);
    }
}
