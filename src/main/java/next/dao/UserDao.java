package next.dao;

import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private final static UserDao userDao = new UserDao();
	
	private UserDao() {
	}
	
	public static UserDao getInstance() {
		return userDao;
	}
	
	public void insert(User user) {
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmt -> {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
		});
	}

	public void update(User user) {
		jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?", 
				user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		String sql = "SELECT userId, password, name, email FROM USERS";
		return jdbcTemplate.query(sql , rs ->
			 new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email"))
			);
	}

	public User findByUserId(String userId) {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return jdbcTemplate.queryForObject(sql, rs -> 
			new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email")),
			userId);
	}
}
