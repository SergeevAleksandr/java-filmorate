package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Genre {
    private long id;
    private String name;
    @JsonCreator
    public Genre(@JsonProperty("id") Long id, String name) {
        this.id = id;
        this.name =name;
    }
}
