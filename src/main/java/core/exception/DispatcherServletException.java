package core.exception;

public class DispatcherServletException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public DispatcherServletException() {
		super();
	}

	public DispatcherServletException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DispatcherServletException(String message, Throwable cause) {
		super(message, cause);
	}

	public DispatcherServletException(String message) {
		super(message);
	}

	public DispatcherServletException(Throwable cause) {
		super(cause);
	}
	
}
