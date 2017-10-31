package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import next.model.User;

public abstract class SelectJdbcManager {
	
	private String sql;
	
	public User executeQuery() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			this.sql = createQuery();
			pstmt = con.prepareStatement(sql);
			setParameters(pstmt);

			rs = pstmt.executeQuery();

			return mapRow(rs);

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	private String createQuery() {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return sql;
	}

	public abstract User mapRow(ResultSet rs) throws SQLException;

	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;

}
