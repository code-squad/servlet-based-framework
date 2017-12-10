package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.InsertJdbcTemplate;
import core.jdbc.UpdateJdbcTemplate;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		InsertJdbcTemplate jdbcTemplate = new InsertJdbcTemplate();
		jdbcTemplate.insert(user);
	}
	
	public void update(User user) throws SQLException {
		UpdateJdbcTemplate jdbcTemplate = new UpdateJdbcTemplate();
		jdbcTemplate.update(user);
	}

	public List<User> findAll() throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT userId, password, name, email FROM USERS";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){
			rs = pstmt.executeQuery();

			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
						rs.getString("email")));
			}
			return users;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public User findByUserId(String userId) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
						rs.getString("email"));
			}
			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
