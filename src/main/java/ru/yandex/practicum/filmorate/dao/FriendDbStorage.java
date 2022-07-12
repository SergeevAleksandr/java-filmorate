package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Component
@Slf4j
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID_USER");
        String email = rs.getString("EMAIL");
        String login = rs.getString("LOGIN");
        String name = rs.getString("NAME");
        LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();

        return User.builder().
                id(id).
                email(email).
                login(login).
                name(name).
                birthday(birthday).
                build();
    }
    @Override
    public Long addFriend(Long userId, Long friendId) {
        String addFriend = "INSERT INTO FRIENDS (FIRST_FRIEND, SECOND_FRIEND) VALUES (?, ?)";
        jdbcTemplate.update(addFriend, userId, friendId);
        log.debug("Добавление {} в друзья {} прошло успешно", friendId, userId);
        return userId;
    }
    @Override
    public Long deleteFriend(Long userId, Long friendId) {
        String deleteFriend = "DELETE FROM FRIENDS WHERE" +
                " FIRST_FRIEND = ? AND SECOND_FRIEND = ?";
        jdbcTemplate.update(deleteFriend, userId, friendId);
        log.debug("Удаление {} из друзей {} прошло успешно", friendId, userId);
        return userId;
    }
    @Override
    public Collection<Long> findUserFriendsIdById(Long userId) {
        String getIdFriendsById = "SELECT SECOND_FRIEND FROM FRIENDS WHERE" +
                " FIRST_FRIEND = ?";
        return jdbcTemplate.query(
                getIdFriendsById,
                (rs, rowNum) -> rs.getLong("SECOND_FRIEND"),
                userId);
    }
    @Override
    public Collection<User> findUserFriendsById(Long userId) {
        String getFriendsById = "SELECT * FROM USERS WHERE ID_USER IN " +
                "(SELECT SECOND_FRIEND FROM FRIENDS WHERE FIRST_FRIEND = ?)";
        return jdbcTemplate.query(getFriendsById, (rs, rowNum) -> makeUser(rs,rowNum), userId);
    }
    @Override
    public Collection<User> findCommonFriends(Long firstUserId, Long secondUserId) {
        String commomFriends = "SELECT * FROM USERS WHERE ID_USER IN " +
                "(SELECT SECOND_FRIEND FROM FRIENDS WHERE FIRST_FRIEND = ? AND SECOND_FRIEND IN " +
                "(Select SECOND_FRIEND FROM FRIENDS WHERE FIRST_FRIEND = ?))";
        return jdbcTemplate.query(
                commomFriends,
                (rs, rowNum) -> makeUser(rs,rowNum),
                firstUserId, secondUserId);
    }
}
