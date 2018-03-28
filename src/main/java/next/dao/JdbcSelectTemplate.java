package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

abstract public class JdbcSelectTemplate {

    public List<User> query(String sql) throws SQLException {
        Connection dbConn= null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            dbConn = ConnectionManager.getConnection();
            pstmt = dbConn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while(rs.next()){
                User user = mapRow(rs);
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

    public User queryForObject(String userId, String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(userId, pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
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

    abstract User mapRow(ResultSet rs) throws SQLException;
    abstract void setValues(String userId, PreparedStatement pstmt) throws SQLException;

}
