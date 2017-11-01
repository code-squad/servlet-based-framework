package core.jdbc;

import java.sql.SQLException;

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
	
	public DataAccessException(SQLException e) {
		super(e.getMessage());
	}

	private static final long serialVersionUID = 1L;
	

}
