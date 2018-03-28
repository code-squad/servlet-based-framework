package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

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

    public static List query(String sql, RowMapper rm) throws DataAccessException {
        List users = new ArrayList<>();
        try (Connection dbConn = ConnectionManager.getConnection();
             PreparedStatement pstmt = dbConn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object user = rm.mapRow(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return users;
    }

    public static Object queryForObject(String sql, RowMapper rm, PreparedStatementSetter pss) throws DataAccessException {
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
}
