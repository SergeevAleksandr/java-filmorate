package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class LikeController {

    private final FilmService filmService;

    @PutMapping("/{id}/like/{userId}")
    public List<Long> addLikeFilm(
            @PathVariable("id") Long filmId,
            @PathVariable("userId") Long userId
    ) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public List<Long> removeLikeFilm(
            @PathVariable("id") Long filmId,
            @PathVariable("userId") Long userId
    ) throws UserNotFoundException {
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Set<Film> findPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count
    ) {
        return filmService.findPopularFilms(count);
    }
}