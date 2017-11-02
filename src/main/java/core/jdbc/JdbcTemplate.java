package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcTemplate {


	public <T> List<T> query(String sql,RowMapper<T> rowmapper, Object...parameter) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			for (int i = 0; i < parameter.length; i++) {
				pstmt.setString(i+1, (String) parameter[i]);
			}
			ResultSet rs = pstmt.executeQuery();

			List<T> values = new ArrayList<>();
			while (rs.next()) {
				values.add(rowmapper.mapRow(rs));
			}
			return values;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rowmapper, Object...parameter) {
		List<T> result = query(sql, rowmapper, parameter);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}
	public void update(String sql, Object...values) {
		try (Connection con = ConnectionManager.getConnection();PreparedStatement pstmt = con.prepareStatement(sql); ) {
			for (int i = 0; i < values.length; i++) {
				pstmt.setString(i+1, (String) values[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
	}


}
