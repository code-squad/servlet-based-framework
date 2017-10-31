package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class JdbcTemplate {
	public void update(String query) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query);){
			setValues(pstmt);
			pstmt.executeUpdate();
		}catch(Exception e) {
			//throw new ~~Exception
			e.printStackTrace();
		}
	}
	public List<User> query(String query) throws SQLException{
		List<User> users = new ArrayList<User>();
		ResultSet rs = null; 
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query)){	
			setValues(pstmt);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				users.add((User) mapRow(rs));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)
				rs.close();
		}
		return users;
	}
	public Object queryForObject(String query) throws SQLException {
		ResultSet rs = null;
		User user = null;
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query)) {
			setValues(pstmt);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user = (User) mapRow(rs);
			}
			return user;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)
				rs.close();
		}
		return user;
	}
	public abstract void setValues(PreparedStatement pstmt) throws SQLException;
	public abstract Object mapRow(ResultSet rs) throws SQLException;
}
