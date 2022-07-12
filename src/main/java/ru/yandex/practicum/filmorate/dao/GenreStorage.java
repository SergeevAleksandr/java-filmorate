package ru.yandex.practicum.filmorate.dao;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface GenreStorage {

    List<Genre> findAll();

    Genre findById(Long genreId) throws ObjectNotFoundException;

    Collection<Genre> getGenreFromFilmId(Long filmId);

    void addGenreForFilm(Long filmId, long genreId);

    void deleteByFilmId(Long filmId);
}
