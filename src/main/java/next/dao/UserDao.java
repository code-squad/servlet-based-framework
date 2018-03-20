package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // 커넥션 객체 생성
            con = ConnectionManager.getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            // Creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
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

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        Connection dbConn = null;
        PreparedStatement pstmt = null;
        try {
            dbConn = ConnectionManager.getConnection();
            String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
            pstmt = dbConn.prepareStatement(sql);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null){
                pstmt.close();
            }
            if(dbConn != null){
                dbConn.close();
            }
        }
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        Connection dbConn= null;
        PreparedStatement pstmt = null;
        List<User> users = new ArrayList<>();
        try {
            dbConn = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS";
            pstmt = dbConn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                User user = new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4));
                users.add(user);
            }
        }finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (dbConn != null) {
                dbConn.close();
            }
        }
        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;

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