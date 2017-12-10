package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
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
		};
		jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?");
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
