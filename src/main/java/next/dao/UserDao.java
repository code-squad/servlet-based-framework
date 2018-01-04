package next.dao;

import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
	public void insert(User user) {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		JDBCLibrary.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) {
		String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
		JDBCLibrary.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT userId, password, name, email FROM USERS";
		RowMapper rm = JDBCLibrary.select(sql);
		for (int i = 0; i < rm.size(); i++) {
			users.add(new User(rm.getValue("userId", i), rm.getValue("password", i), rm.getValue("name", i),
					rm.getValue("email", i)));
		}
		return users;
	}

	public User findByUserId(String userId) {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		RowMapper rm = JDBCLibrary.select(sql, userId);
		User user = null;
		for (int i = 0; i < rm.size(); i++) {
			user = new User(rm.getValue("userId", i), rm.getValue("password", i), rm.getValue("name", i),
					rm.getValue("email", i));
		}
		return user;
	}
}