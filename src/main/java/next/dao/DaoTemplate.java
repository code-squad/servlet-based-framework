package next.dao;

import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;

public class DaoTemplate {
    // JDBC Library 구현
    private String sql;

    public DaoTemplate(String sql) {
        this.sql = sql;
    }

    public int getNumOfParams(){
        return StringUtils.countOccurrencesOf(sql, "?");
    }

    // sql 문에 인자 setting
    public void setParams(PreparedStatement pstmt){
        int c = getNumOfParams();
        while(c > 0){
//            pstmt.setString();
        }
    }

}
