package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public class JdbcTemplate {
	
	public void update(String query, String ...obj) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query);){
			createPreparedStatementSetter(obj).setValues(pstmt);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public <T> List<T> query(String query, RowMapper<T> rm, String...obj){
		try(Connection con = ConnectionManager.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(query);){	
			createPreparedStatementSetter(obj).setValues(pstmt);
			return getResultSetData(pstmt, rm);
		}catch(SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public <T> T queryForObject(String query, RowMapper<T> rm, String...obj){
		return query(query, rm, obj).get(0);
	}
	
	public PreparedStatementSetter createPreparedStatementSetter(String...obj) {
		return (pstmt) -> {
			int index = 1;
			for(String o : obj) {
				pstmt.setString(index++, o);
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
