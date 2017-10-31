package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class JdbcTemplate {
	ResultSet rs;

	
	public List query(String sql) {
		List<User> users = new ArrayList<User>();
		try (Connection con = ConnectionManager.getConnection(); Statement stmt = con.createStatement();) {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				users.add(new User(rs.getString("userId"), rs.getString("password"),  rs.getString("name"), rs.getString("email")));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return users;
	}

	public Object queryForObject(String sql, String userId) {
		User user=null;
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return user;
	}

	public void update(String sql, Object...values) {
		try (Connection con = ConnectionManager.getConnection();PreparedStatement pstmt = con.prepareStatement(sql); ) {
			for (int i = 0; i < values.length; i++) {
				pstmt.setString(i+1, (String) values[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
	}

}
