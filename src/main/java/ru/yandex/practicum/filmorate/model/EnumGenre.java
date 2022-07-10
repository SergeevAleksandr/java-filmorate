package ru.yandex.practicum.filmorate.model;

public enum EnumGenre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    CARTOON("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private final String title;
    EnumGenre(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
