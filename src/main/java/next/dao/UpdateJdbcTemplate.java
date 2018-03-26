package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdateJdbcTemplate {

    public void update(User user, UserDao userDao) throws SQLException {
        Connection dbConn = null;
        PreparedStatement pstmt = null;

        try {
            dbConn = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            pstmt = dbConn.prepareStatement(sql);
            setValuesForUpdate(user, pstmt);
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

    abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;
    abstract String createQueryForUpdate();
}

