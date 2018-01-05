package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.exception.DataAccessException;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pstmtSetter) {
		try (PreparedStatement pstmt = connection(sql)) {
			pstmtSetter.setValues(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(String sql, String... objects) {
		try (PreparedStatement pstmt = connection(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		try (PreparedStatement pstmt = connection(sql)) {
			pstmtSetter.setValues(pstmt);
			try (ResultSet rs = pstmt.executeQuery()) {
				return getResult(rs, rm);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, String... objects) {
		try (PreparedStatement pstmt = connection(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				return getResult(rs, rm);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		try (PreparedStatement pstmt = connection(sql)) {
			pstmtSetter.setValues(pstmt);
			try (ResultSet rs = pstmt.executeQuery();) {
				return getResultForObject(rs, rm);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, String... objects) {
		try (PreparedStatement pstmt = connection(sql)) {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setString(i + 1, objects[i]);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				return getResultForObject(rs, rm);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private PreparedStatement connection(String sql) {
		try (Connection con = ConnectionManager.getConnection()) {
			return con.prepareStatement(sql);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private <T> T getResultForObject(ResultSet rs, RowMapper<T> rm) {
		try {
			T value = null;
			if (rs.next()) {
				value = rm.mapRow(rs);
			}
			return value;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private <T> List<T> getResult(ResultSet rs, RowMapper<T> rm) {
		try {
			List<T> values = new ArrayList<T>();
			while (rs.next()) {
				values.add(rm.mapRow(rs));
			}
			return values;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}