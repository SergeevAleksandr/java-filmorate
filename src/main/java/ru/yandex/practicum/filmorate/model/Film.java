package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.SortComparator;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
@Data
@ToString
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@EqualsAndHashCode
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
    private MPA mpa;
    private Set<Genre> genres;
    private Set<Long> likes;

}
