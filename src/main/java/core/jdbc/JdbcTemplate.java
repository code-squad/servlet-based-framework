package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	private static JdbcTemplate template = new JdbcTemplate();
	public static JdbcTemplate getInstance() {
		return template;
	}
	public <T> List<T> query(String sql, RowMapper<T> rowmapper, Object... parameter) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			setParameter(pstmt, parameter);
			return setReponseQuery(rowmapper, pstmt);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private void setParameter(PreparedStatement pstmt, Object... parameter) throws SQLException {
		for (int i = 0; i < parameter.length; i++) {
			pstmt.setObject(i + 1, parameter[i]);
		}
	}

	private <T> List<T> setReponseQuery(RowMapper<T> rowmapper, PreparedStatement pstmt) throws SQLException {
		ResultSet rs = pstmt.executeQuery();

		List<T> values = new ArrayList<>();
		while (rs.next()) {
			values.add(rowmapper.mapRow(rs));
		}
		rs.close();
		return values;
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowmapper, Object... parameter) {
		List<T> result = query(sql, rowmapper, parameter);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}

	public void update(String sql, KeyHolder keyHolder, Object... parameter) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			setParameter(pstmt, parameter);
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				keyHolder.setId(rs.getLong(1));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

	}

}
