package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.db.exceptions.MultipleDataException;

public class JdbcManager {

	private Connection conn = ConnectionManager.getConnection();


	public void insertObject(String sql, Object... objects) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, objects);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> T find(String sql, RowMapper<T> rm, Object... objects) {
		List<T> objectList = findAll(sql, rm, objects);
		if (objectList.isEmpty()) {
			return null;
		}
		if (objectList.size() > 1) {
			throw new MultipleDataException(MultipleDataException.STANDARD_MULTI_DATA_ERR_MSG); 
		}
		return objectList.get(0);
	}

	public <T> List<T> findAll(String sql, RowMapper<T> rm, Object... objects) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> objectList = new ArrayList<T>();

		try {
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, objects);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				objectList.add(rm.mapRow(rs));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return objectList;
	}

	private void setParameters(PreparedStatement pstmt, Object... objects) {
		try {
			int index = 1;
			for (Object o : objects) {
				pstmt.setString(index++, (String) o);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

}
