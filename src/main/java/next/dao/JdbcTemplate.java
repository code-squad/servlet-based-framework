package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class JdbcTemplate {
	public void update(User user) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(craeteQuery());){
			setValues(pstmt);
			pstmt.executeUpdate();
		}catch(Exception e) {
			//throw new ~~Exception
			e.printStackTrace();
		}
	}
	public abstract void setValues(PreparedStatement pstmt) throws SQLException;
	public abstract String craeteQuery();
}
