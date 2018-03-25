package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
// try with resource 반영
public class UserDao {

    public void insert(User user) throws SQLException {

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            // implement spl statement
            pstmt.executeUpdate();
        }
    }
    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";

        try(Connection dbConn = ConnectionManager.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            pstmt.executeUpdate();
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS";

        List<User> users = new ArrayList<>();

        // Creates a PreparedStatement object for sending
        // parameterized SQL statements to the database.
        try(Connection dbConn = ConnectionManager.getConnection();
        PreparedStatement pstmt = dbConn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                User user = new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4));
                users.add(user);
            }
        }
        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        ResultSet rs = null;

        try(Connection dbConn = ConnectionManager.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sql);
        ) {
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;
        }
    }
}