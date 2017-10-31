package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.FindAllJdbcManager;
import core.jdbc.FindJdbcManager;
import core.jdbc.JdbcManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		String sql = "INSERT INTO USERS VALUES(?,?,?,?)";

		JdbcManager manager = new JdbcManager(sql) {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}

			@Override
			public User mapRow(ResultSet rs) {
				return null;
			}

		};
		manager.executeQuery();
	}

	public void update(User user) throws SQLException {
		User originalUser = findByUserId(user.getUserId());
		originalUser.update(user);
		String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";

		JdbcManager jdbm = new JdbcManager(sql) {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, originalUser.getPassword());
				pstmt.setString(2, originalUser.getName());
				pstmt.setString(3, originalUser.getEmail());
				pstmt.setString(4, originalUser.getUserId());

			}

			@Override
			public User mapRow(ResultSet rs) {
				return null;
			}
		};
		jdbm.executeQuery();
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";
		JdbcManager fjdbm = new JdbcManager(sql) {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {

			}

			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return null;
			}

		};
		return fjdbm.findAll();
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
		JdbcManager sjdbm = new JdbcManager(sql) {

			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				User user = null;
				if (rs.next()) {
					user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
							rs.getString("email"));
				}
				return user;
			}

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
		};

		return sjdbm.find();
	}

}
