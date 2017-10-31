package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.FindAllJdbcManager;
import core.jdbc.JdbcManager;
import core.jdbc.SelectJdbcManager;
import core.jdbc.UpdateJdbcManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcManager manager = new JdbcManager() {

			@Override
			public String createSql() {
				return "INSERT INTO USERS VALUES(?,?,?,?)";
			}

			@Override
			public void setParameter(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}

		};
		manager.insert();
	}

	public void update(User user) throws SQLException {
		User originalUser = findByUserId(user.getUserId());
		originalUser.update(user);

		UpdateJdbcManager ujdbm = new UpdateJdbcManager() {

			@Override
			public String createSql() {
				return "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
			}

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, originalUser.getPassword());
				pstmt.setString(2, originalUser.getName());
				pstmt.setString(3, originalUser.getEmail());
				pstmt.setString(4, originalUser.getUserId());

			}
		};
		ujdbm.executeQuery();
	}

	public List<User> findAll() throws SQLException {
		FindAllJdbcManager fjdbm = new FindAllJdbcManager() {

			@Override
			public String createSql() {
				return "SELECT userId, password, name, email FROM USERS";
			}

		};
		return fjdbm.findAll();
	}

	public User findByUserId(String userId) throws SQLException {
		SelectJdbcManager sjdbm = new SelectJdbcManager() {

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
		return sjdbm.executeQuery();
	}

}
