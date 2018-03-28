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
        JdbcTemplate jdbcTemplate = new JdbcTemplate() { // 익명 자식 객체
            @Override
            void setValues(String query, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            void setValues(String query, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());            }
        };

        jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?");
    }

    public List<User> findAll() throws SQLException {
        JdbcSelectTemplate jdbcSelectTemplate = new JdbcSelectTemplate() {
            @Override
            User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4));
            }

            @Override
            void setValues(String userId, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return jdbcSelectTemplate.query("SELECT userId, password, name, email FROM USERS");
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcSelectTemplate jdbcSelectTemplate = new JdbcSelectTemplate() {
            @Override
            User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4));
            }

            @Override
            void setValues(String userId, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return jdbcSelectTemplate.queryForObject(userId, "SELECT userId, password, name, email FROM USERS WHERE userid=?");
    }
}