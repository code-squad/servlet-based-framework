package next.dao;

import java.sql.SQLException;
import java.util.List;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate insertTemplate = new JdbcTemplate();
		insertTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) throws SQLException {
		JdbcTemplate updateTemplate = new JdbcTemplate();
//		updateTemplate.update("UPDATE USERS set password=?, name=?, email=? WHERE userId=?", (pstmt) -> {
//			pstmt.setString(1, user.getPassword());
//			pstmt.setString(2, user.getName());
//			pstmt.setString(3, user.getEmail());
//			pstmt.setString(4, user.getUserId());
//		});
		updateTemplate.update("UPDATE USERS set password=?, name=?, email=? WHERE userId=?", user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() throws SQLException {
		JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
		return selectJdbcTemplate.query("SELECT UserId, password, name, email FROM USERS", (rs) -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
	}

	public User findByUserId(String userId) throws SQLException {
		JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
//		return (User) selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", 
//				(rs) -> {
//					return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
//							rs.getString("email"));
//				},
//				(pstmt) -> {
//					pstmt.setString(1, userId);
//		});
		return (User) selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", (rs) -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		}, userId);
	}
}