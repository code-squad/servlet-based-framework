package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateJdbcTemplate {

    public void update(User user, UserDao userDao) throws SQLException {
        Connection dbConn = null;
        PreparedStatement pstmt = null;

        try {
            dbConn = ConnectionManager.getConnection();
            String sql = userDao.createQueryForUpdate();
            pstmt = dbConn.prepareStatement(sql);
            userDao.setValuesForUpdate(user, pstmt);
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null){
                pstmt.close();
            }
            if(dbConn != null){
                dbConn.close();
            }
        }
    }
}

