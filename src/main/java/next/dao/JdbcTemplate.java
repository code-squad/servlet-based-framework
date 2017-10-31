package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class JdbcTemplate {
	public void update(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(craeteQuery());
			setValues(pstmt);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null)
				con.close();
			if(pstmt!=null)
				pstmt.close();
		}
	}
	public abstract void setValues(PreparedStatement pstmt) throws SQLException;
	public abstract String craeteQuery();
}
