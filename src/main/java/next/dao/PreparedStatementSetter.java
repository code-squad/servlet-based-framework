package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
	public PreparedStatement setValues(PreparedStatement pstmt) throws SQLException;
}
