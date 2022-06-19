package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserInterface {
    private final InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public Collection<User> findAll() {
        return inMemoryUserStorage.findAll();
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User put(User user) throws UserNotFoundException {
        return inMemoryUserStorage.put(user);
    }
    public User findById(Long id) {
        return inMemoryUserStorage.findById(id);
    }
    public void addFriend(Long id, Long friendId) {

        User user = inMemoryUserStorage.findById(id);
        User friend = inMemoryUserStorage.findById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        log.debug("Пользователи добавлены в друзья id {} и id {}", id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) throws UserNotFoundException {

        User user = inMemoryUserStorage.findById(id);
        User friend = inMemoryUserStorage.findById(friendId);

        if (!user.getFriends().contains(friendId) ||
                !friend.getFriends().contains(id)) {
            throw new UserNotFoundException("Пользователи с id " + id + ", " + friendId + " не являются друзьями.");

        }

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        log.debug("Пользователи удалены из друзей id {} и id - {}", id, friendId);
    }

    public List<User> findAllFriendsUserById(Long id) {

        User user = inMemoryUserStorage.findById(id);

        return findFriendFromUserId(new ArrayList<>(user.getFriends()));
    }

    public List<User> findCommonFriends(Long id, Long otherId) {

        List<User> commonFriends = new ArrayList<>();

        User firstUser = inMemoryUserStorage.findById(id);
        User secondUser = inMemoryUserStorage.findById(otherId);

            List<User> friendsFirstUser = findFriendFromUserId(new ArrayList<>(firstUser.getFriends()));
            List<User> friendsSecondUser = findFriendFromUserId(new ArrayList<>(secondUser.getFriends()));
            for (User i : friendsFirstUser) {
                Iterator<User> iterator = friendsSecondUser.listIterator();
                while (iterator.hasNext()) {
                    if (i.equals(iterator.next())) {
                        commonFriends.add(i);
                        iterator.remove();
                        break;
                    }
            }
        }

        return commonFriends;
    }
    private List<User> findFriendFromUserId(List<Long> idFriends) {
        List<User> userFriends = new ArrayList<>();

        for (Long id : idFriends) {
            userFriends.add(inMemoryUserStorage.findById(id));
        }
        return userFriends;
    }
    public void isExistById(Long id) throws UserNotFoundException {
       inMemoryUserStorage.isExistById(id);
    }
}
