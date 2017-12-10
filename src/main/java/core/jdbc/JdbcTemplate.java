package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter setter) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
		}
	}
	public <T> List<T> query(String sql, PreparedStatementSetter setter, RowMapper<T> rm) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
			return getResultSets(pstmt, rm);
		}
	}

	public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> rm) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
			return getResultSet(pstmt, rm);
		}
	}

	private <T> List<T> getResultSets(PreparedStatement pstmt, RowMapper<T> rm) throws SQLException {
		List<T> list = new ArrayList<T>();
		try (ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rm.mapRow(rs));
			}
			return list;
		}
	}

	private <T> T getResultSet(PreparedStatement pstmt, RowMapper<T> rm) throws SQLException {
		try (ResultSet rs = pstmt.executeQuery()) {
			return rm.mapRow(rs);
		}
	}

}
