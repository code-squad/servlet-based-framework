package next.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.KeyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
    private DataSource ds;

    public JdbcTemplate(DataSource ds) {
        this.ds = ds;
    }

    public void update(String query, KeyHolder holder, PreparedStatementSetter pss) throws DataAccessException {// 변하지 않는 부분
        try (PreparedStatement pstmt = ds.getConnection().prepareStatement(query)) {

            pss.setValues(pstmt);

            pstmt.executeUpdate();

            setId(holder, pstmt);
            log.debug("key : {}", holder.getId());

            // implement spl statement
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private static void setId(KeyHolder holder, PreparedStatement pstmt) throws SQLException {
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            holder.setId(rs.getLong(1));
        }
        rs.close();
    }

    public void update(String query, PreparedStatementSetter pss) throws DataAccessException {
        try (PreparedStatement pstmt = ds.getConnection().prepareStatement(query)) {
            pss.setValues(pstmt);
            // implement spl statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String query, KeyHolder holder, Object... objects) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> setValues(pstmt, objects);

        update(query, holder, pss);
    }

    public void update(String query, Object... objects) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> setValues(pstmt, objects);

        update(query, pss);
    }

    private static void setValues(PreparedStatement pstmt, Object[] objects) throws SQLException {
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
    }

    public  <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        List<T> objects = new ArrayList<>();
        try (PreparedStatement pstmt = ds.getConnection().prepareStatement(sql)) {
            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                T object = rm.mapRow(rs);
                objects.add(object);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return objects;
    }

    public  <T> List<T> query(String sql, RowMapper<T> rm, Object... objects) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> setValues(pstmt, objects);
        return query(sql, rm, pss);
    }

    public  <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        List<T> list = query(sql, rm, pss);
        if (list.size() == 0) return null;
        return list.get(0);
    }

    public  <T> T queryForObject(String sql, RowMapper<T> rm, Object... objects) throws DataAccessException {
        // object -> pss
        PreparedStatementSetter pss = pstmt -> setValues(pstmt, objects);
        return queryForObject(sql, rm, pss);
    }

}
