package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public abstract class FindJdbcManager {

	private String sql;

	public FindJdbcManager(String sql) {
		this.sql = sql;
	}

	public User find() throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = ConnectionManager.getConnection();

		pstmt = con.prepareStatement(sql);
		setParameters(pstmt);
		rs = pstmt.executeQuery();

		return mapRow(rs);
	}

	public abstract User mapRow(ResultSet rs) throws SQLException;

	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;

}
