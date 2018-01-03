package next.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
	private JDBCLibrary jdbcLibrary = new JDBCLibrary();

	public void insert(User user) throws SQLException {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		jdbcLibrary.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) throws SQLException {
		String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
		jdbcLibrary.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT userId, password, name, email FROM USERS";
		RowMapper rm = jdbcLibrary.select(sql);
		for (int i = 0; i < rm.size(); i++) {
			users.add(new User(rm.getValue("userId", i), rm.getValue("password", i), rm.getValue("name", i),
					rm.getValue("email", i)));
		}
		return users;
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		RowMapper rm = jdbcLibrary.select(sql, userId);
		User user = null;
		for (int i = 0; i < rm.size(); i++) {
			user = new User(rm.getValue("userId", i), rm.getValue("password", i), rm.getValue("name", i),
					rm.getValue("email", i));
		}
		return user;
	}
}
