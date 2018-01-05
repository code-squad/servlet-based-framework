package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
	public void insert_(User user) {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, (PreparedStatement pstmt) -> {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
		});
	}

	public void insert(User user) {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update_(User user) {
		String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, (PreparedStatement pstmt) -> {
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getUserId());
		});
	}

	public void update(User user) {
		String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll_() {
		String sql = "SELECT userid, password, name, email FROM USERS";
		List<User> users = new ArrayList<User>();
		JdbcTemplate template = new JdbcTemplate();
		users = template.<User>query(sql, (PreparedStatement pstmt) -> {
		}, (ResultSet rs) -> {
			return new User(rs.getString("userid"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
		return users;
	}

	public List<User> findAll() {
		String sql = "SELECT userid, password, name, email FROM USERS";
		List<User> users = new ArrayList<User>();
		JdbcTemplate template = new JdbcTemplate();
		users = template.<User>query(sql, (ResultSet rs) -> {
			return new User(rs.getString("userid"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
		return users;
	}

	public User findByUserId_(String userId) {
		String sql = "SELECT userid, password, name, email FROM USERS WHERE userid=?";
		User user = null;
		JdbcTemplate template = new JdbcTemplate();
		user = template.<User>queryForObject(sql, (PreparedStatement pstmt) -> {
			pstmt.setString(1, userId);
		}, (ResultSet rs) -> {
			return new User(rs.getString("userid"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
		return user;
	}

	public User findByUserId(String userId) {
		String sql = "SELECT userid, password, name, email FROM USERS WHERE userid=?";
		User user = null;
		JdbcTemplate template = new JdbcTemplate();
		user = template.<User>queryForObject(sql, (ResultSet rs) -> {
			return new User(rs.getString("userid"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		}, userId);
		return user;
	}
}