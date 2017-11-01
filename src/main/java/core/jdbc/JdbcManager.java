package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.User;

public class JdbcManager {

	private String sql;
	private Connection conn = ConnectionManager.getConnection();

	public JdbcManager(String sql) {

		this.sql = sql;
	}

	public void insertObject(PreparedStatementSetter pstmts) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(this.sql);
			pstmts.generatePstmt(pstmt);

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

	public User find(PreparedStatementSetter pstmts, RowMapper<User> rm) {
		PreparedStatement pstmt = null;
		ResultSet rs;

		conn = ConnectionManager.getConnection();
		try {

			pstmt = conn.prepareStatement(sql);
			pstmts.generatePstmt(pstmt);
			rs = pstmt.executeQuery();
			return rm.mapRow(rs);
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;

	}

	public List<User> findAll(RowMapper<List<User>> rm) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = ConnectionManager.getConnection();

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			return rm.mapRow(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
