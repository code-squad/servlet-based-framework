package next.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.JdbcManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		String sql = "INSERT INTO USERS VALUES(?,?,?,?)";

		JdbcManager manager = new JdbcManager(sql) {

		};
		manager.insertObject(pstmt -> {
			try {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void update(User user) throws SQLException {
		User originalUser = findByUserId(user.getUserId());
		originalUser.update(user);
		String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";

		JdbcManager jdbm = new JdbcManager(sql) {

		};
		jdbm.insertObject(pstmt -> {

			try {
				pstmt.setString(1, originalUser.getPassword());
				pstmt.setString(2, originalUser.getName());
				pstmt.setString(3, originalUser.getEmail());
				pstmt.setString(4, originalUser.getUserId());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";
		JdbcManager fjdbm = new JdbcManager(sql) {

		};
		return fjdbm.findAll(rs -> {
			List<User> userlist = new ArrayList<>();
			while (rs.next()) {
				userlist.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email")));
			}
			return userlist;
		});
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
		JdbcManager sjdbm = new JdbcManager(sql) {

		};

		return sjdbm.find(pstmt -> {
			try {
				pstmt.setString(1, userId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}, rs -> {
			if (rs.next()) {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
			return null;
		});
	}

}
