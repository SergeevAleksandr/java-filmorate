package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    public Film filmTest;
    public Film filmTest2;
    public Film filmTest3;
    public Film filmTest4;
    public Film filmTest5;
    public Film filmTest6;
    public Film filmTest7;
    public Film filmTest8;

    FilmController filmController = new FilmController();
    @BeforeEach
    public void beforeEach(){
        filmTest = new Film("Фильм_1","ОписаниеФильма", LocalDate.of(2000,12,12),100);
        filmTest2 = new Film("Фильм_2","ОписаниеФильма2", LocalDate.of(1895,12,27),200);
        filmTest3 = new Film("Фильм_3","ОписаниеФильма3", LocalDate.of(1895,12,29),200);
        filmTest4 = new Film("Фильм_4","ОписаниеФильма4", LocalDate.of(1895,12,28),200);
        filmTest5 = new Film("","ОписаниеФильма5", LocalDate.of(1895,12,28),200);
        filmTest6 = new Film("Фильм_6","Много символов Много символов Много символов Много символов Много символов " +
                "Много символов Много символов Много символов Много символов Много символов Много символов Много символов Много символов " +
                "Много символов Много символов Много символов Много символов Много символов Много символов Много символов Много символов " +
                "Много символов Много символов Много символов Много символов Много символов Много символов Много символов Много символов " +
                "Много символов Много символов Много символов Много символов Много символов Много символов Много символов Много символов ",
                LocalDate.of(1895,12,28),200);
        filmTest7 = new Film("Фильм_7","ОписаниеФильма7", LocalDate.of(2000,12,12),0);
        filmTest8 = new Film("Фильм_8","ОписаниеФильма8", LocalDate.of(2000,12,12),1);
    }
    @Test
    void createOneFilm() {
        filmController.create(filmTest);
        assertEquals(1,filmController.findAll().size());
    }
    @Test
    void createTwoFilms() {
        filmController.create(filmTest);
        filmController.create(filmTest3);
        assertEquals(2,filmController.findAll().size());
    }
    @Test
    void createOldReleaseDateFilm() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(filmTest2));
        assertEquals("дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());
    }
    @Test
    void createFailNameFilm() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(filmTest5));
        assertEquals("название не может быть пустым.", exception.getMessage());
    }
    @Test
    void createFailDescriptionFilm() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(filmTest6));
        assertEquals("максимальная длина описания — 200 символов", exception.getMessage());
    }
    @Test
    void createFailDurationFilm() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(filmTest7));
        assertEquals("продолжительность фильма должна быть положительной", exception.getMessage());
    }
    @Test
    void UpdateFilm(){
        filmController.create(filmTest);
        filmTest8.setId(filmTest.getId());
        filmController.put(filmTest8);
        assertEquals(1,filmController.findAll().size());
    }
    @Test
    void UpdateFilmFail(){
        filmController.create(filmTest);
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> filmController.put(filmTest3));
        assertEquals("Нет фильма с таким ключём", exception.getMessage());
    }
}
