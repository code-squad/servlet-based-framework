package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcManager {

	private String sql;

	public void insert() throws SQLException {
		String sql = createSql();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			setParameter(pstmt);

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public abstract String createSql();

	public abstract void setParameter(PreparedStatement pstmt) throws SQLException;

}
