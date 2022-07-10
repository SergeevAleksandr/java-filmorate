package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

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
        //this.name = translator(name);
        this.name = EnumGenre.values()[(int) (id - 1)].getTitle();
    }
    private String translator(String name){
        Map<String, String> map = new HashMap<String, String>();
        map.put("COMEDY","Комедия");
        map.put("DRAMA","Драма");
        map.put("CARTOON","Мультфильм");
        map.put("THRILLER","Триллер");
        map.put("DOCUMENTARY","Документальный");
        map.put("ACTION","Боевик");
        return map.get(name);
    }
}
