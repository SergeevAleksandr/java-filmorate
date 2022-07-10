package ru.yandex.practicum.filmorate.exception;

import org.springframework.stereotype.Component;

public class FilmNotFoundException extends Exception{
    public FilmNotFoundException() {
        super();
    }

    public FilmNotFoundException(String message) {
        super(message);
    }
}
