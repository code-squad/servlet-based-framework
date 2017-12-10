package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.model.User;

public class UpdateJdbcTemplate {
	public void update(User user) throws SQLException {
		String sql = createQueryForUpdate();
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			setValuesForUpdate(user, pstmt);
		}
	}
	
	private void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getUserId());
		pstmt.executeUpdate();
	}

	private String createQueryForUpdate() {
		return "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
	}
}
