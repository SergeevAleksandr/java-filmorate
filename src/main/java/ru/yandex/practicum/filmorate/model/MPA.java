package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MPA {
    private String name;
    private long id;
    public MPA(long id) {
        this.id = id;
    }
}