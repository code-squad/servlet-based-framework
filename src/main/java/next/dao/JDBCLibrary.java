package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;

public class JDBCLibrary {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int update(String sql, String... setValues) throws SQLException {
		createPstmt(sql);
		int parameterIndex = 1;
		for (String setValue : setValues) {
			pstmt.setString(parameterIndex++, setValue);
		}
		int resultCount = pstmt.executeUpdate();
		close();
		return resultCount;
	}

	public RowMapper select(String sql, String... setValues) throws SQLException{
		createPstmt(sql);
		int parameterIndex = 1;
		for (String setValue : setValues) {
			pstmt.setString(parameterIndex++, setValue);
		}
		rs = pstmt.executeQuery();
		RowMapper rm = new RowMapper(rs);
		close();
		return rm;
	}

	private void createPstmt(String sql) throws SQLException {
		con = ConnectionManager.getConnection();
		pstmt = con.prepareStatement(sql);
	}

	private void close() throws SQLException {
		if (con != null)
			con.close();
		if (pstmt != null)
			pstmt.close();
		if (rs != null)
			rs.close();
	}
}