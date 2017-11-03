package core.jdbc;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface PreparedStatementSetter {
	public void generatePstmt(PreparedStatement pstmt);
}
