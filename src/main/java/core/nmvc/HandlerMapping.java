package core.nmvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {

	Object getController(HttpServletRequest req);

}
