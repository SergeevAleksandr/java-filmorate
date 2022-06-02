package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

    public Film(String name, String description, LocalDate releaseDate, long duration) {
        this.id = createId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int createId() {
        this.id = id + 1;
        return this.id;
    }
}
