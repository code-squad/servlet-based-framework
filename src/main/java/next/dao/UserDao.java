package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public void update(User user) throws SQLException {
		User originalUser = findByUserId(user.getUserId());
		originalUser.update(user);

		Connection con = null;
		PreparedStatement pstmt = null;

		con = ConnectionManager.getConnection();
		String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
		pstmt = con.prepareStatement(sql);

		pstmt.setString(1, originalUser.getPassword());
		pstmt.setString(2, originalUser.getName());
		pstmt.setString(3, originalUser.getEmail());
		pstmt.setString(4, originalUser.getUserId());

		pstmt.executeUpdate();

		if (pstmt != null) {
			pstmt.close();
		}

		if (con != null) {
			con.close();
		}

	}

	public List<User> findAll() throws SQLException {
		List<User> userlist = new ArrayList<User>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = ConnectionManager.getConnection();
		String sql = "SELECT userId, password, name, email FROM USERS";
		pstmt = con.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			userlist.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email")));
		}

		return userlist;
	}

	public User findByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}

			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}
