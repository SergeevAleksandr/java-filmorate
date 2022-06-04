package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class UserControllerTest {
    public User userTest;
    public User userTest2;
    public User userTest3;
    public User userTest4;
    public User userTest5;
    public User userTest6;
    public User userTest7;

    UserController userController = new UserController();
    @BeforeEach
    public void beforeEach(){
        userTest = new User("LoginTest","Name","email@email.com",LocalDate.of(1990,1,1));
        userTest2 = new User("LoginTest2","Name","email@email2.com",LocalDate.now());
        userTest3 = new User("","Name","email@email3.com",LocalDate.of(1990,1,1));
        userTest4 = new User("Login Test4","Name","email@email4.com",LocalDate.of(1990,1,1));
        userTest5 = new User("LoginTest5","Name","emailemail5.com",LocalDate.of(1990,1,1));
        userTest6 = new User("LoginTest6","Name","email@email6.com",LocalDate.now().plusDays(1));
        userTest7 = new User("LoginTest7","Name","email@email.com",LocalDate.of(1990,1,1));
    }
    @Test
    void createOneUser() {
        userController.create(userTest);
        assertEquals(1,userController.findAll().size());
    }
    @Test
    void createTwoUsers() {
        userController.create(userTest);
        userController.create(userTest2);
        assertEquals(2,userController.findAll().size());
    }
    @Test
    void createFailUserLoginTestOne() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.create(userTest3));
        assertEquals("логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }
    @Test
    void createFailLoginTestTwo() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.create(userTest4));
        assertEquals("логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }
    @Test
    void createFailUserEmail() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.create(userTest5));
        assertEquals("Адрес электронной почты не может быть пустым и должен быть в правильном формате.", exception.getMessage());
    }
    @Test
    void createFailUserBirthday() {
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.create(userTest6));
        assertEquals("дата рождения не может быть в будущем", exception.getMessage());
    }
    @Test
    void createExistingUserEmail() {
        userController.create(userTest);
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.create(userTest7));
        assertEquals("Пользователь с электронной почтой email@email.com уже зарегистрирован.", exception.getMessage());
    }
    @Test
    void UpdateUser(){
        userController.create(userTest);
        userTest7.setId(userTest.getId());
        userController.put(userTest7);
        assertEquals(1,userController.findAll().size());
    }
    @Test
    void UpdateUserFail(){
        userController.create(userTest);
        final RuntimeException exception = assertThrows(
                ValidationException.class,
                () -> userController.put(userTest7));
        assertEquals("Нет пользователя с таким ключём", exception.getMessage());
    }
}
