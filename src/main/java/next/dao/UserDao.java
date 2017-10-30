package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = createQueryForInsert(user, con);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

	private PreparedStatement createQueryForInsert(User user, Connection con) throws SQLException {
		PreparedStatement pstmt;
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		pstmt = con.prepareStatement(sql);
		setValuesForInsert(user, pstmt);
		return pstmt;
	}

	private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getEmail());
	}

    public void update(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = createQueryForUpdate(user, con);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

	private PreparedStatement createQueryForUpdate(User user, Connection con) throws SQLException {
		PreparedStatement pstmt;
		String sql = "UPDATE USERS SET password=?,name=?,email=? WHERE userId=?";
		pstmt = con.prepareStatement(sql);
		setValuesForUpdate(user, pstmt);
		return pstmt;
	}

	private void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getUserId());
	}

    public List<User> findAll() throws SQLException {
        Connection con = null;
        ResultSet rs ;
        Statement stmt;
        List<User> users = new ArrayList<User>();
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM USERS";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
    		while (rs.next()) {
    			String userId = rs.getString("userId");
    			String password = rs.getString("password");
    			String name = rs.getString("name");
    			String email = rs.getString("email");
    			users.add(new User(userId,password,name,email));
    		}
        } finally {

            if (con != null) {
                con.close();
            }
        }

        return users;
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
