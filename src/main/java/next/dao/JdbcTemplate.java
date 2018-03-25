package next.dao;

import core.jdbc.ConnectionManager;
import next.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {

    public static void execute(String sql, String... params) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            setParams(pstmt, params);
            // implement sql statement
            pstmt.executeUpdate();
        } catch (SQLException e){ // A conversion from compile-time exception to run-time exception
            throw new DataAccessException(e);
        }
    }
    // 외부에서 사용되는 메소드 아니기 때문에 예외 전환 필요 없음.
    private static void setParams(PreparedStatement pstmt, String... params) throws SQLException {
        int count = 1;
        for(String s : params){
            pstmt.setString(count, s);
            count++;
        }
    }

}

