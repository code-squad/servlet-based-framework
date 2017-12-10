package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.model.User;

public class InsertJdbcTemplate {
	public void insert(User user) throws SQLException {
		String sql = createQueryForInsert();
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValuesForInsert(user, pstmt);
		}
	}
	
	private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getEmail());

		pstmt.executeUpdate();
	}

	private String createQueryForInsert() {
		return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
	}
}
