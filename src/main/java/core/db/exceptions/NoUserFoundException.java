package core.db.exceptions;

public class NoUserFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String STD_NOUSER_ERROR_MSG = "검색된 사용자가 없습니다!";
	
	public NoUserFoundException(String msg) {
		super(msg);
	}

}
