package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Long, Film> films = new HashMap<>();
    private static final LocalDate DateReleaseMin = LocalDate.of(1895, 12, 28);
    protected long id;


    public Map<Long, Film> getFilms() {
        return films;
    }

    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    public Film create(Film film) {
        check(film);
        for (long i:films.keySet()){
            if (films.get(i).getName().equals(film.getName())){
                throw new ValidationException("Фильм с таким именем-" +
                        film.getId() + " уже существует.");
            }
        }
        film.setId(++id);
        films.put(film.getId(), film);
        log.debug("Добавлен фильм {}", film.getName());
        return film;
    }

    public Film put(Film film) throws FilmNotFoundException {
        check(film);
        Long filmId = film.getId();
        isExistById(filmId);
        films.put(filmId, film);
        log.debug("Обновлен фильм {}", film.getName());
        return film;
    }

    public void check(Film film){
        if (film.getReleaseDate().isBefore(DateReleaseMin)) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }
    public void isExistById(Long id) throws FilmNotFoundException {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException("Фильм с таким Id " + id + " не найден");
        }
    }

    public Film getFilmById(Long id){
        return films.get(id);
    }

}
