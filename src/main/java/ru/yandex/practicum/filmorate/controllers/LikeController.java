package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class LikeController {
    private final FilmService filmService;
    @PutMapping("/{id}/like/{userId}")
    public Long addLikeFilm(
            @PathVariable("id") Long filmId,
            @PathVariable("userId") Long userId
    ) throws ObjectNotFoundException {
        return filmService.addLike(filmId, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Long removeLikeFilm(
            @PathVariable("id") Long filmId,
            @PathVariable("userId") Long userId
    ) throws ObjectNotFoundException {
        return filmService.deleteLike(filmId, userId);
    }
    @GetMapping("/popular")
    public Collection<Film> findPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count
    ) {
        return filmService.findPopularFilms(count);
    }
}