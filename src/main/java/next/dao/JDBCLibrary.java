package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.jdbc.ConnectionManager;
import next.exception.JDBCLibraryException;

public class JDBCLibrary {

	public static int update(String sql, String... setValues) {
		PreparedStatement pstmt = createPstmt(sql);
		try {
			int parameterIndex = 1;
			for (String setValue : setValues) {
				pstmt.setString(parameterIndex++, setValue);
			}
			int resultCount = pstmt.executeUpdate();
			return resultCount;
		} catch (SQLException e) {
			throw new JDBCLibraryException(e);
		} finally {
			close(pstmt);
		}
	}

	public static RowMapper select(String sql, String... setValues) {
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = createPstmt(sql);
			int parameterIndex = 1;
			for (String setValue : setValues) {
				pstmt.setString(parameterIndex++, setValue);
			}
			rs = pstmt.executeQuery();
			RowMapper rm = new RowMapper(rs);
			return rm;
		} catch (SQLException e) {
			throw new JDBCLibraryException(e);
		} finally {
			close(rs);
		}
	}

	private static PreparedStatement createPstmt(String sql) {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			return pstmt;
		} catch (SQLException e) {
			throw new JDBCLibraryException(e);
		}
	}

	private static void close(ResultSet rs) {
		try {
			Statement st = rs.getStatement();
			Connection con = st.getConnection();
			if (con != null)
				con.close();
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			throw new JDBCLibraryException(e);
		}
	}

	private static void close(Statement st) {
		try {
			Connection con = st.getConnection();
			if (con != null)
				con.close();
			if (st != null)
				st.close();
		} catch (SQLException e) {
			throw new JDBCLibraryException(e);
		}
	}
}