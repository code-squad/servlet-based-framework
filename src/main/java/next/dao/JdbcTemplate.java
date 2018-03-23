package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {

    public static void execute(String sql, String... params) throws SQLException {

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            setParams(pstmt, params);
            // implement sql statement
            pstmt.executeUpdate();
        }
    }

    private static void setParams(PreparedStatement pstmt, String... params) throws SQLException {
        int count = 1;
        for(String s : params){
            pstmt.setString(count, s);
            count++;
        }
    }

}

