package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
	public void update(String sql) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValues(pstmt);
		}
	}
	public <T> List<T> query(String sql) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValues(pstmt);
			return getResultSets(pstmt);
		}
	}

	public <T> T queryForObject(String sql) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValues(pstmt);
			return getResultSet(pstmt);
		}
	}

	private <T> List<T> getResultSets(PreparedStatement pstmt) throws SQLException {
		List<T> list = new ArrayList<T>();
		try (ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(mapRow(rs));
			}
			return list;
		}
	}

	private <T> T getResultSet(PreparedStatement pstmt) throws SQLException {
		try (ResultSet rs = pstmt.executeQuery()) {
			return mapRow(rs);
		}
	}

	public abstract <T> T mapRow(ResultSet rs) throws SQLException;
	
	public abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
