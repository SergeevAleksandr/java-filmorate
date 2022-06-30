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
public class Film {
    private long id;
    @NotBlank
    private String name;
    @NotEmpty
    @Size(max=200)
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @Positive
    private long duration;

    private Set<Long> likes = new HashSet<>();
    public Film(String name, String description, LocalDate releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
