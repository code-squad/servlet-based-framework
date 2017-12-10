package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.model.User;

public abstract class JdbcTemplate {
	public void update(String sql) throws SQLException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValues(pstmt);
		}
	}

	public abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
