package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcSelectTemplate {

    public List<User> find(String query, GetValues gv, String... params) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            if(params.length > 0) setValues(params[0], pstmt);

            rs = pstmt.executeQuery();

            while(rs.next()){
                gv.getValues(users, rs);
            }

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
        return users;
    }

    abstract void setValues(String userId, PreparedStatement pstmt) throws SQLException;

}
