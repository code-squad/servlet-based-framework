package next.dao;

import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	public void insert(User user) {
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmt -> {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.executeUpdate();
		});
	}

	public void update(User user) {
		jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?", pstmt -> {
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getUserId());
			pstmt.executeUpdate();
		});
	}

	public List<User> findAll() {
		return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", rs ->
			 new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email"))
			);
	}

	public User findByUserId(String userId) {
		return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rs -> 
			new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email")),
			userId);
	}
}
