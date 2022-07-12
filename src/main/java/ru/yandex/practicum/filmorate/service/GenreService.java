package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreDbStorage genreDao;

    public Collection<Genre> findAll() {
        return genreDao.findAll();
    }

    public Genre findById(Long genreId) throws ObjectNotFoundException {
        return genreDao.findById(genreId);
    }

}