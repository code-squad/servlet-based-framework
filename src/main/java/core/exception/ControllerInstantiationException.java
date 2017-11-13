package core.exception;

public class ControllerInstantiationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public ControllerInstantiationException() {
		super();
	}

	public ControllerInstantiationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ControllerInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerInstantiationException(String message) {
		super(message);
	}

	public ControllerInstantiationException(Throwable cause) {
		super(cause);
	}

}
