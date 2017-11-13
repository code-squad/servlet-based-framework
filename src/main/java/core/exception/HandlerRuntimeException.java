package core.exception;

@FunctionalInterface
public interface HandlerRuntimeException<T, R> {
	T handling(R param);
}
