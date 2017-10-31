package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.model.User;

public abstract class UpdateJdbcManager {
	
	public void executeQuery() throws SQLException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con = ConnectionManager.getConnection();
		String sql = createSql();
		pstmt = con.prepareStatement(sql);
		
		setParameters(pstmt);
		
		pstmt.executeUpdate();
		
		if (pstmt != null) {
			pstmt.close();
		}
		
		if (con != null) {
			con.close();
		}
	}


	public abstract String createSql();

	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
}

