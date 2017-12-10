package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter setter) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}
	
	public void update(String sql, Object... values) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setObjects(pstmt, values);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public <T> List<T> query(String sql, PreparedStatementSetter setter, RowMapper<T> rm) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
			return getResultSetList(pstmt, rm);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rm, Object... values) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setObjects(pstmt, values);
			return getResultSetList(pstmt, rm);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> rm) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
			return getResultSet(pstmt, rm);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object... values) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setObjects(pstmt, values);
			return getResultSet(pstmt, rm);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	private void setObjects(PreparedStatement pstmt, Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			pstmt.setObject(i + 1, values[i]);
		}
	}
	
	private <T> List<T> getResultSetList(PreparedStatement pstmt, RowMapper<T> rm) throws SQLException {
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
			if (rs.next()) {
				return rm.mapRow(rs);
			}
			return null;
		}
	}
}
