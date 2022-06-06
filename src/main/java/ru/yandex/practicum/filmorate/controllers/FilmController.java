package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate DateReleaseMin = LocalDate.of(1895, 12, 28);
    private int id;

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(++id);
        check(film);
        for (int i:films.keySet()){
            if (films.get(i).getName().equals(film.getName())){
                throw new ValidationException("Фильм с таким именем-" +
                        film.getId() + " уже существует.");
            }
        }
        films.put(film.getId(), film);
        log.debug("Добавлен фильм {}", film.getName());
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        check(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Нет фильма с таким ключём");
        }
        films.put(film.getId(), film);
        log.debug("Обновлен фильм {}", film.getName());
        return film;
    }
    private void check(Film film){
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("название не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(DateReleaseMin)) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <=0) {
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
    }
}
