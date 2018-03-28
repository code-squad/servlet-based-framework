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

    public static void update(String query, PreparedStatementSetter pss) throws SQLException {// 변하지 않는 부분
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // 커넥션 객체 생성
            con = ConnectionManager.getConnection();
            // Creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            pstmt = con.prepareStatement(query);
            pss.setValues(pstmt);
            // implement spl statement
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public static List<User> query(String sql, RowMapper rm) throws SQLException {
        Connection dbConn= null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            dbConn = ConnectionManager.getConnection();
            pstmt = dbConn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while(rs.next()){
                User user = (User)rm.mapRow(rs);
                users.add(user);
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (dbConn != null) {
                dbConn.close();
            }
        }
        return users;
    }

    public static User queryForObject(String sql, RowMapper rm, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return (User)rm.mapRow(rs);
            }
            return null;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
