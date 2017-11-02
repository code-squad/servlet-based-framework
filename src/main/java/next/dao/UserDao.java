package next.dao;

import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcManager;
import next.model.User;

public class UserDao {
	public void insert(User user) {
		String sql = "INSERT INTO USERS VALUES(?,?,?,?)";
		JdbcManager manager = new JdbcManager();

		//manager.insertObject(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
		manager.insertObject("USERS", user);
	}

	public void update(User user) {
		User originalUser = findByUserId(user.getUserId());
		originalUser.update(user);

		String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
		JdbcManager jdbm = new JdbcManager();
		jdbm.insertObject(sql, originalUser.getPassword(), originalUser.getName(), originalUser.getEmail(),
				originalUser.getUserId());
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";
		JdbcManager fjdbm = new JdbcManager() {

		};
		return fjdbm.findAll(sql, rs -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
	}

	public User findByUserId(String userId) {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
		JdbcManager sjdbm = new JdbcManager();

		return sjdbm.find(sql, rs -> {

			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));

		}, userId);
	}

}
