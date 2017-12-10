package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.SelectJdbcTemplate;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
				pstmt.executeUpdate();
			}
			@Override
			public <T> T mapRow(ResultSet rs) throws SQLException {
				return null;
			}
		};
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
	}

	public void update(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(){
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
				pstmt.executeUpdate();
			}
			@Override
			public <T> T mapRow(ResultSet rs) throws SQLException {
				return null;
			}
		};
		jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?");
	}

	public List<User> findAll() throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				User user = null;
				if (rs.next()) {
					user = new User(
							rs.getString("userId"), 
							rs.getString("password"), 
							rs.getString("name"),
							rs.getString("email"));
				}
				return user;
			}
		};
		String sql = "SELECT userId, password, name, email FROM USERS";
		return jdbcTemplate.query(sql);
	}

	public User findByUserId(String userId) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				User user = null;
				if (rs.next()) {
					user = new User(
							rs.getString("userId"), 
							rs.getString("password"), 
							rs.getString("name"),
							rs.getString("email"));
				}
				return user;
			}
		};
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return jdbcTemplate.queryForObject(sql);
	}
}
