package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.exception.DataAccessException;
import next.model.User;

public class UserDao {

    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate.execute(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws DataAccessException {
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate.execute(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() throws DataAccessException {
        String sql = "SELECT userId, password, name, email FROM USERS";

//        FindOperator op = (s)  -> getUsers(s);
        return JdbcSelectTemplate.execute(sql, (s)  -> getUsers(s));
    }

    public User findByUserId(String userId) throws DataAccessException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

//        FindOperator op = (s)  -> getUsers(s);
        return JdbcSelectTemplate.execute(sql, (s)  -> getUsers(s), userId).get(0);
    }

    private List<User> getUsers(ResultSet s) throws SQLException {
        List<User> users = new ArrayList<>();
        while(s.next()){
            User user = new User(s.getString("userId"), s.getString(2), s.getString(3), s.getString(4));
            users.add(user);
        }
        return users;
    }
}