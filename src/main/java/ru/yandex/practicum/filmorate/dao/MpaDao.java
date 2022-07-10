package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String getMpa = "SELECT * FROM MPA";
    private static final String findMpaFromId = "SELECT * FROM MPA WHERE ID_MPA = ?";
    private static final String findNameMpafromId = "SELECT NAME FROM MPA WHERE ID_MPA = ?";

    static MPA makeMpa(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID_MPA");
        String name = rs.getString("NAME");

        return MPA.builder().
                id(id).
                name(name).
                build();
    }
   public Collection<MPA> findAll() {
        return jdbcTemplate.query(getMpa, MpaDao::makeMpa);
    }
    public MPA findById(Long mpaId) throws ObjectNotFoundException {
        List<MPA> mpas = jdbcTemplate.query(findMpaFromId, (rs, rowNum) -> makeMpa(rs, rowNum), mpaId);
        if (mpas.size() < 1) {
            throw new ObjectNotFoundException("mpa", mpaId);
        }
        return mpas.get(0);
    }

    public String findNameById(Long mpaId) throws ObjectNotFoundException {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(findNameMpafromId, mpaId);

        if (rs.next()) {
            return rs.getString("NAME");
        }
        throw new ObjectNotFoundException("mpa", mpaId);
    }
}