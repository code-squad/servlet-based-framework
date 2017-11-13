package core.exception;

public class HandlerControllerInstantiationException implements HandlerRuntimeException<Object, Class<?>> {

	@Override
	public Object handling(Class<?> param) {
		try {
			return param.newInstance();
		} catch (Exception e) {
			throw new ControllerInstantiationException();
		}
	}

}
