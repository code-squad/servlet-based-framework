package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {

    public static void update(String query, PreparedStatementSetter pss) throws DataAccessException {// 변하지 않는 부분
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            // 커넥션 객체 생성
            // Creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            pss.setValues(pstmt);
            // implement spl statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static void update(String query, Object... objects) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)
        ) {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i + 1, objects[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> List<T> query(String sql, RowMapper<T> rm) throws DataAccessException {
        List<T> objects = new ArrayList<T>();
        try (Connection dbConn = ConnectionManager.getConnection();
             PreparedStatement pstmt = dbConn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                T object = rm.mapRow(rs);
                objects.add(object);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return objects;
    }

    public static <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        try (Connection dbConn = ConnectionManager.getConnection();
             PreparedStatement pstmt = dbConn.prepareStatement(sql)
        ) {
            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rm.mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> T queryForObject(String sql, RowMapper<T> rm, Object... objects) throws DataAccessException {
        try (Connection dbConn = ConnectionManager.getConnection();
             PreparedStatement pstmt = dbConn.prepareStatement(sql)
        ) {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i + 1, objects[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rm.mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
