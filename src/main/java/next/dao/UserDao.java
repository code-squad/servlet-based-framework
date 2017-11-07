package next.dao;

import java.sql.SQLException;
import java.util.List;
import next.model.User;

public class UserDao {
	private static final UserDao userDao = new UserDao();
	private static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
	private UserDao() {
	}

	public static UserDao getInstance() {
		return userDao;
	}

	public void insert(User user) throws SQLException {
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(),
				user.getName(), user.getEmail());
	}

	public void update(User user) throws SQLException {		
		jdbcTemplate.update("UPDATE USERS set password=?, name=?, email=? WHERE userId=?", user.getPassword(),
				user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() throws SQLException {
		return jdbcTemplate.query("SELECT UserId, password, name, email FROM USERS", (rs) -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
	}

	public User findByUserId(String userId) throws SQLException {
		return (User) jdbcTemplate
				.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", (rs) -> {
					return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
							rs.getString("email"));
				}, userId);
	}

	public void delete(User user) {
		jdbcTemplate.update("DELETE FROM USERS WHERE userId=?", user.getUserId());
	}
}