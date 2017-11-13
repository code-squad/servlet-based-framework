package core.di.exceptions;

public class NoBeanException extends RuntimeException{

	private static final long serialVersionUID = -4189130501689432679L;
	
	public NoBeanException(Class<?> clazz) {
		super("No suitable bean found for Class " + clazz.getName());
	}
}
