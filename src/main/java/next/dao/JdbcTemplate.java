package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class JdbcTemplate {
	public void update(String query, PreparedStatementSetter pstmtSetter) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query);){
			pstmtSetter.setValues(pstmt);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void update(String query, Object ...obj) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query);){
			createPreparedStatementSetter(obj).setValues(pstmt);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<User> query(String query, RowMapper<User> rm){
		List<User> users = new ArrayList<User>();
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();){	
			while(rs.next()) {
				users.add(rm.mapRow(rs));
			}
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
		return users;
	}
	
	public Object queryForObject(String query, RowMapper<User> rm,  PreparedStatementSetter pstmtSetter){
		ResultSet rs = null;
		User user = null;
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmtSetter.setValues(pstmt);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user = rm.mapRow(rs);
			}
			return user;
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
		}
	}
	
	public Object queryForObject(String query, RowMapper<User> rm, Object...obj){
		ResultSet rs = null;
		User user = null;
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query)) {
				createPreparedStatementSetter(obj).setValues(pstmt);
				rs = pstmt.executeQuery();
			if(rs.next()) {
				user = rm.mapRow(rs);
			}
			return user;
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
		}
	}
	
	public PreparedStatementSetter createPreparedStatementSetter(Object...obj) {
		return (pstmt) -> {
			int index = 1;
			for(Object o : obj) {
				pstmt.setString(index++, (String)o);
				//이게 이렇게 객체가 아닌 String을 받아오는게 맞나?
			}
		};
	}
}
