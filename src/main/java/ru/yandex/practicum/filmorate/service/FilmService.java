package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private InMemoryFilmStorage inMemoryFilmStorage;
    private InMemoryUserStorage inMemoryUserStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage,InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public Collection<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    public Film getFilmById(Long id) throws FilmNotFoundException {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film put(Film film) throws FilmNotFoundException {
        return inMemoryFilmStorage.put(film);
    }

    public void check(Film film) {
        inMemoryFilmStorage.check(film);
    }

    public List<Long> addLike(Long filmId, Long userId) throws FilmNotFoundException, UserNotFoundException {

        Film film = inMemoryFilmStorage.getFilmById(filmId);

        film.getLikes().add(inMemoryUserStorage.findById(userId).getId());

        return new ArrayList<>(film.getLikes());
    }

    public List<Long> deleteLike(Long filmId, Long userId) throws UserNotFoundException,
            FilmNotFoundException {

        Film film = inMemoryFilmStorage.getFilmById(filmId);

        if (!film.getLikes().contains(userId)) {
            throw new UserNotFoundException("Лайк от пользователя с id - " + userId + " отсутствует.");
        }

        film.getLikes().remove(userId);

        return new ArrayList<>(film.getLikes());
    }
    public Set<Film> findPopularFilms(Integer count) {

        return inMemoryFilmStorage.findAll().stream().
                sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()).
                limit(count).collect(Collectors.toSet());
    }
}
