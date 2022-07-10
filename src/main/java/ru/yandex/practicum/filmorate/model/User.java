package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
    private long id;
    private String name;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty(message = "логин не может содержать пробелы.")
    @NotNull(message = "логин не может быть пустым")
    private String login;
    @Past
    private LocalDate birthday;
}