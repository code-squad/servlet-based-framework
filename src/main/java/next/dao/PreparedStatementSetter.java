package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
	public void setValues(PreparedStatement pstmt) throws SQLException;
}
