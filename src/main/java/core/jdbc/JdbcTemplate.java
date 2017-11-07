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
				pstmt.setObject(i+1,  parameter[i]);
			}
			return setReponseQuery(rowmapper, pstmt);
		} catch (SQLException e) {
			throw new DataAccessException(e);
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

    public void update(PreparedStatementCreator psc, KeyHolder holder) {
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = psc.createPreparedStatement(conn);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                holder.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
