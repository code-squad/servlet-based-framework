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
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(String sql, String... objects) {
		update(sql, pstmt -> {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
		});
	}

	public <T> List<T> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			try (ResultSet rs = pstmt.executeQuery()) {
				return getResult(rs, rm);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, String... objects) {
		return query(sql, pstmt -> {
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
		}, rm);
	}

	public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rm) {
		return query(sql, pstmtSetter, rm).get(0);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, String... objects) {
		return query(sql, rm, objects).get(0);
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