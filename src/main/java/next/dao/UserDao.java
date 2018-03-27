package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                pstmt.setString(4, user.getUserId());
            }
        };

        jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?");
    }

    public List<User> findAll() throws SQLException {
        JdbcSelectTemplate jdbcSelectTemplate = new JdbcSelectTemplate() {
            @Override
            void setValues(String userId, PreparedStatement pstmt) throws SQLException {
            }
        };
        return jdbcSelectTemplate.find(createQueryForFindAll(), (users, rs) -> getValues(users, rs));
    }

    public User findUserById(String userId) throws SQLException {
        JdbcSelectTemplate jdbcSelectTemplate = new JdbcSelectTemplate() {
            @Override
            void setValues(String userId, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return jdbcSelectTemplate.find(createQueryForFindUser(), (users, rs) -> getValues(users, rs), userId).get(0);
    }

    void getValues(List<User> users, ResultSet rs) throws SQLException {
        User user = new User(rs.getString("userId"), rs.getString(2), rs.getString(3), rs.getString(4));
        users.add(user);
    }

    private String createQueryForFindAll() {
        return "SELECT userId, password, name, email FROM USERS";
    }

    private String createQueryForFindUser() {
        return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    }
}