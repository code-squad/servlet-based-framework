package core.di.exceptions;

public class NoPrimaryKeyException extends RuntimeException{

	private static final long serialVersionUID = -5478483147878098235L;
	public NoPrimaryKeyException (Class<?> clazz) {
		super("no primary key found for class " + clazz.getName());
	}

}
