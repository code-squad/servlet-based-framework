package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	Connection con = ConnectionManager.getConnection();

	public void insert(User user) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "INSERT INTO USERS (password, name, email, userId) VALUES (?, ?, ?, ?)";
		template.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());

	}

	public void update(User user) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "UPDATE USERS SET password=?,name=?,email=? WHERE userId=?";
		template.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		JdbcTemplate template = new JdbcTemplate();

		String sql = "SELECT * FROM USERS";
		return template.query(sql);
	}

	public User findByUserId(String userId) {
		JdbcTemplate template = new JdbcTemplate();

		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return (User) template.queryForObject(sql, userId);

	}
}
