package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

   // private static final String getAllLikes = "SELECT ID_USER FROM LIKE_FILMS WHERE ID_USER = ?";
    //private static final String addLike = "INSERT INTO LIKE_FILMS (ID_FILM, ID_USER) VALUES (?, ?)";
   // private static final String deleteLike = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ? AND ID_USER = ?";
   // private static final String deleteAllLikes = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ?";
    //private static final String deleteAllUserLikes = "DELETE FROM LIKE_FILMS WHERE ID_USER = ?";

    @Override
    public Long addLike(Long filmId, Long userId) {
        String addLike = "INSERT INTO LIKE_FILMS (ID_FILM, ID_USER) VALUES (?, ?)";
        jdbcTemplate.update(addLike, filmId, userId);
        return filmId;
    }
    @Override
    public Set<Long> findAllLikesFilm(Long filmId) {
        String getAllLikes = "SELECT ID_USER FROM LIKE_FILMS WHERE ID_USER = ?";
        return  new HashSet<>(jdbcTemplate.query(
                getAllLikes,
                (rs, rowNum) -> rs.getLong("ID_USER"),
                filmId));
    }
    @Override
    public Long deleteLike(Long filmId, Long userId) {
        String deleteLike = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ? AND ID_USER = ?";
        jdbcTemplate.update(deleteLike, filmId, userId);
        return filmId;
    }
    @Override
    public Long deleteAllLikesForFilm(Long filmId) {
        String deleteAllLikes = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ?";
        jdbcTemplate.update(deleteAllLikes, filmId);
        return filmId;
    }
    @Override
    public Long deleteAllLikesForUser(Long userId) {
        String deleteAllUserLikes = "DELETE FROM LIKE_FILMS WHERE ID_USER = ?";
        jdbcTemplate.update(deleteAllUserLikes, userId);
        return userId;
    }
}