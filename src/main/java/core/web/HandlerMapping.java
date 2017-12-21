package core.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping<T> {
	Optional<T> getHandler(HttpServletRequest request);
}
