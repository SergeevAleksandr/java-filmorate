package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;

public interface FilmInterface {
    Collection<Film> findAll() throws ObjectNotFoundException;

    Film getFilmById(Long id) throws ObjectNotFoundException;

    Film create(Film film);
}
