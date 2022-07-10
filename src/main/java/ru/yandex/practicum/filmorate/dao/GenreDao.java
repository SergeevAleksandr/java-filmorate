package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final static String getAllGenre = "SELECT * FROM GENRES";
    private final static String findGenreId = "SELECT * FROM GENRES WHERE ID_GENRE = ?";
    private final static String getGenreFromFilmId = "SELECT G.ID_GENRE,G.NAME\n" +
            "FROM GENRE_FILMS AS GF\n" +
            "LEFT JOIN GENRES G on G.ID_GENRE = GF.ID_GENRE\n" +
            "WHERE ID_FILM = ?"
            +"ORDER BY  G.ID_GENRE";
    private final static String addGenreFilm = "INSERT INTO GENRE_FILMS (ID_FILM, ID_GENRE) VALUES (?, ?)";
    private static final String deleteGenreFilm = "DELETE FROM GENRE_FILMS WHERE ID_FILM = ?";
    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID_GENRE");
        String name = rs.getString("NAME");

        return Genre.builder().
                id(id).
                name(name).
                build();
    }
    public List<Genre> findAll() {
        return  jdbcTemplate.query(getAllGenre, GenreDao::makeGenre);
    }
    public Genre findById(Long genreId) throws ObjectNotFoundException {
        List<Genre> genres = jdbcTemplate.query(findGenreId, (rs, rowNum) -> makeGenre(rs, rowNum), genreId);
        if (genres.size() < 1) {
            throw new ObjectNotFoundException("genre", genreId);
        }
        return genres.get(0);
    }
    public Collection<Genre> getGenreFromFilmId(Long filmId) throws ObjectNotFoundException {
        return  new HashSet<>(jdbcTemplate.query(getGenreFromFilmId, (rs, rowNum) -> makeGenre(rs, rowNum), filmId));

    }
    protected void addGenreForFilm(Long filmId, long genreId) {
        jdbcTemplate.update(addGenreFilm, filmId, genreId);
    }
    protected void deleteByFilmId(Long filmId) {
        jdbcTemplate.update(deleteGenreFilm, filmId);
    }

}