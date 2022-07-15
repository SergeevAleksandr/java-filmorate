package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends SQLException {
    private final String name;
    private final Long id;

    public ObjectNotFoundException(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public ObjectNotFoundException(String name) {
        this.name = name;
        this.id = 0l;
    }
}
