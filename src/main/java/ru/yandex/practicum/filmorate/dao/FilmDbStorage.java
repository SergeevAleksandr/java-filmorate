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
import ru.yandex.practicum.filmorate.storage.FilmStorage;
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
    private static GenreDao genreDao;
    private static MpaDao mpaDao;
    private static LikeDao likeDao;

    private static final String getAllFilms = "SELECT * FROM FILMS";
    private static final String getFilmById ="SELECT * FROM FILMS WHERE ID_FILM = ?";
    private static final String addFilm = "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATA, DURATION, MPA_ID)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String updateFilm = "UPDATE FILMS SET NAME=?, DESCRIPTION=?, RELEASE_DATA=?," +
            " DURATION=?, MPA_ID=? WHERE ID_FILM=?";
    private static final String findPopularFilms =
            "SELECT f.ID_FILM, f.NAME, f.DESCRIPTION, f.RELEASE_DATA,f.DURATION, f.MPA_ID, COUNT(likes.ID_USER)" +
            " FROM FILMS AS f " +
            " LEFT JOIN LIKE_FILMS AS likes ON f.ID_FILM = likes.ID_FILM " +
            " GROUP BY f.ID_FILM, f.NAME, f.DESCRIPTION, f.RELEASE_DATA, f.DURATION, f.MPA_ID " +
            " ORDER BY COUNT(likes.ID_USER) DESC" +
            " LIMIT ?";

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDao genreDao, MpaDao mpaDao,LikeDao likeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
        this.likeDao = likeDao;
    }
    static Film makeFilm(ResultSet rs,int rowNum) throws SQLException {
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
                genres(genre.isEmpty() ? null : genre).
                build();
    }

    @Override
    public Film findByID(long filmId) throws ObjectNotFoundException {
        final List<Film> films = jdbcTemplate.query(getFilmById,FilmDbStorage::makeFilm,filmId);
        if (films.size() < 1){
            throw new ObjectNotFoundException("films",filmId);
        }
        return films.get(0);
    }
    @Override
    public Collection<Film> findAll() throws ObjectNotFoundException {
        return jdbcTemplate.query(getAllFilms,(rs, rowNum) -> makeFilm(rs, rowNum));
    }
    @Override
    public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
        log.debug("Фильм обновлен, ID - {}", film.getId());
        return film;
    }

    public Collection<Film> findPopularFilms(Integer maxSize) {
        return new LinkedHashSet<>(jdbcTemplate.query(findPopularFilms, (rs, rowNum) -> makeFilm(rs,rowNum), maxSize));
    }
    public Long addLike(Long filmId, Long userId) {
        return likeDao.addLike(filmId, userId);
    }
    public Long deleteLike(Long filmId, Long userId) {
        return likeDao.deleteLike(filmId, userId);
    }
}