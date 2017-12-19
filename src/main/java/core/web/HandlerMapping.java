package core.web;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	Object getHandler(HttpServletRequest request);
}
