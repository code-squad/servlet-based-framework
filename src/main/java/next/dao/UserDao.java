package next.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	private Connection con = ConnectionManager.getConnection();
	private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

	private static UserDao userDao = new UserDao();

	public static UserDao getInstance() {
		return userDao;
	}

	public void insert(User user) {
		String sql = "INSERT INTO USERS (password, name, email, userId) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new KeyHolder();

		jdbcTemplate.update(sql, keyHolder, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());

	}

	public void update(User user) {
		String sql = "UPDATE USERS SET password=?,name=?,email=? WHERE userId=?";
		KeyHolder keyHolder = new KeyHolder();

		jdbcTemplate.update(sql, keyHolder, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());

	}

	public <T> List<T> findAll() {
		String sql = "SELECT * FROM USERS";

		return jdbcTemplate.query(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}

		});
	}

	public <T> T findByUserId(String userId) {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";

		return (T) jdbcTemplate.queryForObject(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}

		}, userId);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((con == null) ? 0 : con.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDao other = (UserDao) obj;
		if (con == null) {
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		return true;
	}

}
