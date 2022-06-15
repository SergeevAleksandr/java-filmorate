package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.util.Collection;

public interface FilmStorage {
    Collection<Film> findAll();

    Film create(@Valid @RequestBody Film film);

    Film put(@Valid @RequestBody Film film) throws FilmNotFoundException;

    void check(Film film);
}
