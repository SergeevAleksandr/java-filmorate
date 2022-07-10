package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String getAlllikes = "SELECT ID_USER FROM LIKE_FILMS WHERE ID_USER = ?";
    private static final String addLike = "INSERT INTO LIKE_FILMS (ID_FILM, ID_USER) VALUES (?, ?)";
    private static final String deleteLike = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ? AND ID_USER = ?";
    private static final String deleteAllLikes = "DELETE FROM LIKE_FILMS WHERE ID_FILM = ?";
    private static final String deleteAllUserLikes = "DELETE FROM LIKE_FILMS WHERE ID_USER = ?";

    public Long addLike(Long filmId, Long userId) {
        jdbcTemplate.update(addLike, filmId, userId);
        return filmId;
    }
    public Set<Long> findAllLikesFilm(Long filmId) {
        return  new HashSet<>(jdbcTemplate.query(
                getAlllikes,
                (rs, rowNum) -> rs.getLong("ID_USER"),
                filmId));
    }
    public Long deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update(deleteLike, filmId, userId);
        return filmId;
    }
    public Long deleteAllLikesForFilm(Long filmId) {
        jdbcTemplate.update(deleteAllLikes, filmId);
        return filmId;
    }
    public Long deleteAllLikesForUser(Long userId) {
        jdbcTemplate.update(deleteAllUserLikes, userId);
        return userId;
    }
}