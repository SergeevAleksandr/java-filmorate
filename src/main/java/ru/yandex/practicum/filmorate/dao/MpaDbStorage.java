package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;


    static MPA makeMpa(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID_MPA");
        String name = rs.getString("NAME");

        return MPA.builder().
                id(id).
                name(name).
                build();
    }
    @Override
   public Collection<MPA> findAll() {
        String getMpa = "SELECT * FROM MPA";
        return jdbcTemplate.query(getMpa, MpaDbStorage::makeMpa);
    }
    @Override
    public MPA findById(Long mpaId) throws ObjectNotFoundException {
        String findMpaFromId = "SELECT * FROM MPA WHERE ID_MPA = ?";
        List<MPA> mpas = jdbcTemplate.query(findMpaFromId, (rs, rowNum) -> makeMpa(rs, rowNum), mpaId);
        if (mpas.size() < 1) {
            throw new ObjectNotFoundException("mpa", mpaId);
        }
        return mpas.get(0);
    }
    @Override
    public String findNameById(Long mpaId) throws ObjectNotFoundException {
        String findNameMpaFromId = "SELECT NAME FROM MPA WHERE ID_MPA = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(findNameMpaFromId, mpaId);

        if (rs.next()) {
            return rs.getString("NAME");
        }
        throw new ObjectNotFoundException("mpa", mpaId);
    }
}