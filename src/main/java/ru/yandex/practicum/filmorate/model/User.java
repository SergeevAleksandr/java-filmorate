package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class User {
    private int id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public User(String login, String name, String email, LocalDate birthday) {
        this.id = createId();
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

  /**  public User(String login, String name,int id, String email, LocalDate birthday) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }**/

    public int createId() {
        this.id = id+1;
        return this.id;
    }
}