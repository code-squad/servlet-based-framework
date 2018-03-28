package next.dao;

import java.sql.SQLException;
import java.util.List;

import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {
        JdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) throws SQLException {
        JdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?", pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public List<User> findAll() throws SQLException {
        return JdbcTemplate.query("SELECT userId, password, name, email FROM USERS", rs ->
                new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4)));
    }

    public User findByUserId(String userId) throws SQLException {
        return JdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rs ->
                new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4)),
                pstmt ->  pstmt.setString(1, userId)
        );
    }
}