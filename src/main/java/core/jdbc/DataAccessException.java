package core.jdbc;


public class DataAccessException extends RuntimeException{

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
	
//	public static void closePreparedStatement(PreparedStatement pstmt) {
//        if (pstmt != null) {
//            pstmt.close();
//        }
//	}
//	public static void closeConnection(Connection con) throws SQLException {
//        if (con != null) {
//            con.close();
//        }
//	}
}
