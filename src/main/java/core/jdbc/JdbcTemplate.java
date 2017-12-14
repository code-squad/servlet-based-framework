package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	public void update(PreparedStatementCreator psc, KeyHolder holder) throws DataAccessException {
	      try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = psc.createPreparedStatement(con)) {
	          pstmt.executeUpdate();
	          ResultSet rs = pstmt.getGeneratedKeys();
	          if (rs.next()) {
	              holder.setId(rs.getLong(1));
	          	}
	          rs.close();
	      } catch (SQLException e) {
	          throw new DataAccessException("답변을 삽입에서 문제 발생");
	      }
	  }
	
	public void update(String sql, PreparedStatementSetter setter) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setter.setValues(pstmt);
			pstmt.executeUpdate();
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

	public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> rm) throws DataAccessException {
		List<T> list = query(sql, setter, rm);
		return list.size() == 0 ? null : list.get(0);
	}

	public void update(String sql, Object... values) throws DataAccessException {
		update(sql, createSetter(values));
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, Object... values) throws DataAccessException {
		return query(sql, createSetter(values), rm);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, Object... values) throws DataAccessException {
		return queryForObject(sql, createSetter(values), rm);
	}
	
	private PreparedStatementSetter createSetter(Object... values) {
		return pstmt -> {
			for (int i = 0; i < values.length; i++) {
				pstmt.setObject(i + 1, values[i]);
			}
		};
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
}
