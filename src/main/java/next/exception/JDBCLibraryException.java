package next.exception;

public class JDBCLibraryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public JDBCLibraryException(String message) {
		super(message);
	}
	
	public JDBCLibraryException(Exception e) {
		super(e);
	}
}
