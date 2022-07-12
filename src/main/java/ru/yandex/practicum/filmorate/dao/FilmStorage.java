package ru.yandex.practicum.filmorate.dao;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface FilmStorage {

    Film findByID(long filmId) throws ObjectNotFoundException;

    Collection<Film> findAll() throws ObjectNotFoundException;

    Film create(@Valid @RequestBody Film film);

    Film update(Film film) throws ObjectNotFoundException;

    Collection<Film> findPopularFilms(Integer maxSize);

    Long addLike(Long filmId, Long userId);

    Long deleteLike(Long filmId, Long userId);

    Long deleteFilm(Long filmId);
}
