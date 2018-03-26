package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class InsertJdbcTemplate {

    public void insert(User user, UserDao userDao) throws SQLException {// 변하지 않는 부분
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // 커넥션 객체 생성
            con = ConnectionManager.getConnection();
            String sql = createQueryForInsert();
            // Creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            pstmt = con.prepareStatement(sql);
            setValuesForInsert(user, pstmt);
            // implement spl statement
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
    abstract void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;
    abstract String createQueryForInsert();
}
