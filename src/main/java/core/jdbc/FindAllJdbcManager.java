package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public abstract class FindAllJdbcManager {
	
	public List<User> findAll() throws SQLException {
		List<User> userlist = new ArrayList<User>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = ConnectionManager.getConnection();
		String sql = createSql();
		pstmt = con.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			userlist.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email")));
		}

		return userlist;
	}
	
	public abstract String createSql();

}
