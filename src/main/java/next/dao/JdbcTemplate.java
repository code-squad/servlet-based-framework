package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public class JdbcTemplate {
	
	public void update(String query, Object ...obj) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query);){
			createPreparedStatementSetter(obj).setValues(pstmt);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public <T> List<T> query(String query, RowMapper<T> rm, Object...obj){
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query);){	
			createPreparedStatementSetter(obj).setValues(pstmt);
			return getResultSetData(pstmt, rm);
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public <T> T queryForObject(String query, RowMapper<T> rm, Object...obj){
		List<T> list = query(query, rm, obj);
		return list.isEmpty() ? null : list.get(0);
	}
	
	public PreparedStatementSetter createPreparedStatementSetter(Object...obj) {
		return (pstmt) -> {
			int index = 1;
			for(Object o : obj) {
				pstmt.setObject(index++, o);
			}
		};
	}
	
	public <T> List<T> getResultSetData(PreparedStatement pstmt, RowMapper<T> rm){
		List<T> lists = new ArrayList<T>();
		try(ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				lists.add(rm.mapRow(rs));
			}
			return lists;
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
