package core.nmvc;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	  Optional<?> getHandler(HttpServletRequest request);
}
