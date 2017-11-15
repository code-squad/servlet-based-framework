package core.exception;

public class BeanInstantiateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BeanInstantiateException() {
		super();
	}

	public BeanInstantiateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeanInstantiateException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanInstantiateException(String message) {
		super(message);
	}

	public BeanInstantiateException(Throwable cause) {
		super(cause);
	}

}
