package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcSelectTemplate {

    public static List<User> execute(String sql, FindOperator op, String... params) throws SQLException {
        ResultSet rs;
        try(Connection dbConn = ConnectionManager.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sql)
        ) {
            setParams(pstmt, params);
            rs = pstmt.executeQuery();
            return op.find(rs);
        }
    }

    private static void setParams(PreparedStatement pstmt, String... params) throws SQLException {
        int count = 1;
        for(String s : params){
            pstmt.setString(count, s);
            count++;
        }
    }
}
