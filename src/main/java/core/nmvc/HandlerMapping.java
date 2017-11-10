package core.nmvc;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import next.controller.LegacyController;

public interface HandlerMapping {
	  Optional<?> getHandler(HttpServletRequest request);
}
