package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreStorage {

    List<Genre> findAll();

    Genre findById(Long genreId) throws ObjectNotFoundException;

    Collection<Genre> getGenreFromFilmId(Long filmId);

    void addGenreForFilm(Long filmId, long genreId);

    void deleteByFilmId(Long filmId);
}
