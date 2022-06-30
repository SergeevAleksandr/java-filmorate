package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
public class User {
    private long id;
    @NotEmpty(message = "логин не может содержать пробелы.")
    @NotNull(message = "логин не может быть пустым")
    private String login;
    private String name;
    @Email
    @NotEmpty
    private String email;
    @Past
    private LocalDate birthday;

    public Set<Long> friends = new HashSet<>();


    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}