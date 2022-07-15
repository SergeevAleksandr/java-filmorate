package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import java.util.Collection;

public interface MpaStorage {

    Collection<MPA> findAll();

    MPA findById(Long mpaId) throws ObjectNotFoundException;

    String findNameById(Long mpaId) throws ObjectNotFoundException;
}
