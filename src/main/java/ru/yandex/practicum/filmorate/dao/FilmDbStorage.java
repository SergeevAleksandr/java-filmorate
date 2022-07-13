package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@Component
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate  jdbcTemplate;
    private static GenreStorage genreDao;
    private static MpaStorage mpaDao;
    private static LikeStorage likeDao;
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreStorage genreDao, MpaStorage mpaDao, LikeStorage likeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
        this.likeDao = likeDao;
    }
    public static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID_FILM");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATA").toLocalDate();
        long duration = rs.getLong("DURATION");
        MPA mpa = mpaDao.findById(rs.getLong("MPA_ID"));
        Set<Genre> genre = (Set<Genre>) genreDao.getGenreFromFilmId(id);
        Set<Long> likes = likeDao.findAllLikesFilm(id);
        return Film.builder().
                id(id).
                name(name).
                description(description).
                releaseDate(releaseDate).
                duration(duration).
                mpa(mpa).
                likes(likes).
                genres(genre).
                build();
    }

    @Override
    public Film findByID(long filmId) throws ObjectNotFoundException {
        String getFilmById ="SELECT * FROM FILMS WHERE ID_FILM = ?";
        final List<Film> films = jdbcTemplate.query(getFilmById, FilmDbStorage::makeFilm,filmId);
        if (films.size() < 1){
            throw new ObjectNotFoundException("films",filmId);
        }
        return films.get(0);
    }
    @Override
    public Collection<Film> findAll() throws ObjectNotFoundException {
        String getAllFilms = "SELECT * FROM FILMS";
        return jdbcTemplate.query(getAllFilms,(rs, rowNum) -> FilmDbStorage.makeFilm(rs, rowNum));
    }
    @Override
    public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String addFilm = "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATA, DURATION, MPA_ID)" +
                " VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement stmt = connection.prepareStatement(addFilm, new String[]{"ID_FILM"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setLong(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        long newId = keyHolder.getKey().longValue();
        film.setId(newId);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreDao.addGenreForFilm(newId, genre.getId());
            }
        }
        return film;
    }
    @Override
    public Film update(Film film) throws ObjectNotFoundException {
        findByID(film.getId());
        String updateFilm = "UPDATE FILMS SET NAME=?, DESCRIPTION=?, RELEASE_DATA=?," +
                " DURATION=?, MPA_ID=? WHERE ID_FILM=? ";
        jdbcTemplate.update(updateFilm,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        genreDao.deleteByFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreDao.addGenreForFilm(film.getId(), genre.getId());
            }
        }
        log.info("Фильм обновлен, ID - {}", film.getId());
        return findByID(film.getId());
    }
    @Override
    public Long deleteFilm(Long filmId){
        String deleteFilm = "DELETE FROM FILMS WHERE ID_FILM = ?";
        jdbcTemplate.update(deleteFilm, filmId);
        log.info("Записи с фильмом успешно удалены, его id - {}", filmId);
        return filmId;
    }

    @Override
    public Collection<Film> findPopularFilms(Integer maxSize) {
        String findPopularFilms =
                "SELECT f.ID_FILM, f.NAME, f.DESCRIPTION, f.RELEASE_DATA,f.DURATION, f.MPA_ID, COUNT(likes.ID_USER)" +
                        " FROM FILMS AS f " +
                        " LEFT JOIN LIKE_FILMS AS likes ON f.ID_FILM = likes.ID_FILM " +
                        " GROUP BY f.ID_FILM, f.NAME, f.DESCRIPTION, f.RELEASE_DATA, f.DURATION, f.MPA_ID " +
                        " ORDER BY COUNT(likes.ID_USER) DESC" +
                        " LIMIT ?";
        log.info("Популярные фильмы получены,ограничение по записям", maxSize);
        return new HashSet<>(jdbcTemplate.query(findPopularFilms, (rs, rowNum) -> FilmDbStorage.makeFilm(rs,rowNum), maxSize));
    }
    @Override
    public Long addLike(Long filmId, Long userId) {
        return likeDao.addLike(filmId, userId);
    }
    @Override
    public Long deleteLike(Long filmId, Long userId) {
        return likeDao.deleteLike(filmId, userId);
    }
}