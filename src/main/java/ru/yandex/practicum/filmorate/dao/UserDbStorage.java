package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
//@RequiredArgsConstructor
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate  jdbcTemplate;
    private FriendDbStorage friendDao;
    //private static final String getAllUser = "SELECT * FROM USERS";
    //private static final String getUserById = "SELECT * FROM USERS WHERE ID_USER = ?";
   /** private static final String getUserId ="SELECT ID_USER FROM USERS WHERE" +
            " EMAIL=? AND LOGIN=? AND NAME=? AND BIRTHDAY=?";*/
    /**private static final String addUser = "INSERT INTO USERS (NAME, EMAIL, LOGIN, BIRTHDAY)" +
            " VALUES (?, ?, ?, ?)";*/
    /**private static final String updateUser = "UPDATE USERS SET" +
            " NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? WHERE ID_USER = ?";*/
   // private static final String deleteUser = "DELETE FROM USERS WHERE ID_USER = ?";

    public UserDbStorage(JdbcTemplate jdbcTemplate, FriendDbStorage friendDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendDao = friendDao;
    }

    static User makeUser(ResultSet rs,int rowNum) throws SQLException{
        long id = rs.getLong("ID_USER");
        String email = rs.getString("EMAIL");
        String login = rs.getString("LOGIN");
        String name = rs.getString("NAME");
        LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();
        //Set<Long> friend = new HashSet<>(friendDao.findAllIdFriendsUserById(id));

        return User.builder().
                id(id).
                email(email).
                login(login).
                name(name).
                birthday(birthday).
                build();
    }
    @Override
    public User findByID(long userID) throws ObjectNotFoundException {
        String getUserById = "SELECT * FROM USERS WHERE ID_USER = ?";
        final List<User> users = jdbcTemplate.query(getUserById,UserDbStorage::makeUser,userID);
        if (users.size() < 1){
            throw new ObjectNotFoundException("user",userID);
        }
        return users.get(0);
    }
    @Override
    public  List<User> findAll() throws ObjectNotFoundException {
        String getAllUser = "SELECT * FROM USERS";
        final List<User> users = jdbcTemplate.query(getAllUser,UserDbStorage::makeUser);
        if (users.size()==0){
            throw new ObjectNotFoundException("users");
        }
        return users;
    }
    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection connection) -> {
            String addUser = "INSERT INTO USERS (NAME, EMAIL, LOGIN, BIRTHDAY)" +
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(addUser, new String[]{"ID_USER"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLogin());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }
    @Override
    public User update(User user) throws ObjectNotFoundException {
        findByID(user.getId());
        String updateUser = "UPDATE USERS SET" +
                " NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? WHERE ID_USER = ?";
        jdbcTemplate.update(updateUser,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        log.debug("Пользователь обновлен, ID_USER - {}", user.getId());
        return user;
    }
    @Override
    public Long delete(Long userId) {
        String deleteUser = "DELETE FROM USERS WHERE ID_USER = ?";
        jdbcTemplate.update(deleteUser, userId);
        log.debug("Запись с пользователем успешно удалена, его id - {}", userId);
        return userId;
    }
    /**Методы друзей**/
    @Override
    public Long addFriend(Long userId, Long friendId){
        return friendDao.addFriend(userId,friendId);
    }
    @Override
    public Collection findUserFriendsById(Long userId){
        return friendDao.findUserFriendsById(userId);
    }
    @Override
    public Collection findUserFriendsIdById(Long userId){
        return friendDao.findUserFriendsIdById(userId);
    }
    @Override
    public Long deleteFriend(Long userId, Long friendId){
       return friendDao.deleteFriend(userId,friendId);
    }
    @Override
    public Collection<User> findCommonFriends(Long firstUserId, Long secondUserId) {
        return friendDao.findCommonFriends(firstUserId,secondUserId);
    }
}
