package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    		JdbcTemplate insertTemplate = new JdbcTemplate() {
				@Override
				public void setValues(User user, PreparedStatement pstmt) throws SQLException {
					pstmt.setString(1, user.getUserId());
					pstmt.setString(2, user.getPassword());
					pstmt.setString(3, user.getName());
					pstmt.setString(4, user.getEmail());
				}
				@Override
				public String craeteQuery() {
					return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
				}};
		insertTemplate.update(user);
    }
    
    public void update(User user) throws SQLException {
    		JdbcTemplate updateTemplate = new JdbcTemplate() {
				@Override
				public void setValues(User user, PreparedStatement pstmt) throws SQLException {
					// TODO Auto-generated method stub
					pstmt.setString(1, user.getPassword());
			    		pstmt.setString(2, user.getName());
			    		pstmt.setString(3, user.getEmail());
			    		pstmt.setString(4, user.getUserId());
				}
				@Override
				public String craeteQuery() {
					// TODO Auto-generated method stub
					return "UPDATE USERS set password=?, name=?, email=? WHERE userId=?";
				}
    		};
    		updateTemplate.update(user);
    }	

    public List<User> findAll() throws SQLException {
    		List<User> list = new ArrayList<User>();
    		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        		con = ConnectionManager.getConnection();
        		String sql = "SELECT UserId, password, name, email FROM USERS";
        		pstmt = con.prepareStatement(sql);
        		rs = pstmt.executeQuery();
        		while(rs.next()) {
        			list.add(new User(rs.getString("userId"), rs.getString("password"), 
        					rs.getString("name"), rs.getString("email")));
        		}
        }catch(Exception e) {
        		e.printStackTrace();
        }finally {
        		if(con != null) 
        			con.close();
        		if(pstmt != null)
        			pstmt.close();
        		if(rs != null)
        			rs.close();
        }
        return list;
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;
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
}
