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
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmtSetter.setValues(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(String sql, String... objects) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			ArrayList<T> values = new ArrayList<T>();
			pstmtSetter.setValues(pstmt);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					values.add(rm.mapRow(rs));
				}
				return values;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, String... objects) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			ArrayList<T> values = new ArrayList<T>();
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					values.add(rm.mapRow(rs));
				}
			}
			return values;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			T value = null;
			pstmtSetter.setValues(pstmt);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					value = rm.mapRow(rs);
				}
			}
			return value;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, String... objects) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			T value = null;
			for (int i = 0; i < objects.length; i++) {
				pstmt.setString(i + 1, objects[i]);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					value = rm.mapRow(rs);
				}
			}
			return value;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}