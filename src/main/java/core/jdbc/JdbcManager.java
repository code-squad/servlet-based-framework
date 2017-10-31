package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public abstract class JdbcManager {

	private String sql;
	private Connection conn = ConnectionManager.getConnection();

	public JdbcManager(String sql) {

		this.sql = sql;
	}

	public void executeQuery() throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(this.sql);
			setParameters(pstmt);

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public User find() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		conn = ConnectionManager.getConnection();
		try {

			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt);
			rs = pstmt.executeQuery();

			user = mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return user;

	}
	
	public List<User> findAll(){
		List<User> userlist = new ArrayList<User>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = ConnectionManager.getConnection();
		try {
		pstmt = con.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			userlist.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email")));
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return userlist;
	}

	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;

	public abstract User mapRow(ResultSet rs) throws SQLException;

}
