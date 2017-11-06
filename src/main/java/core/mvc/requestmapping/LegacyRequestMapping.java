package core.mvc.requestmapping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import core.annotation.RequestMethod;
import core.mvc.controller.LegacyControllerInterface;
import core.mvc.controller.CreateUserController;
import core.mvc.controller.HomeController;
import core.mvc.controller.LoginController;
import core.mvc.controller.LoginPostController;
import core.mvc.controller.UserJoinFormController;
import core.nmvc.HandlerMapping;

public class LegacyRequestMapping implements HandlerMapping {

	private static final LegacyRequestMapping rm = new LegacyRequestMapping();
	private Map<RequestLine, LegacyControllerInterface> controllers = new HashMap<>();

	private LegacyRequestMapping() {
	}

	public static LegacyRequestMapping getInstance() {
		rm.initialize();
		return rm;
	}

	public void addController(LegacyControllerInterface controller, RequestLine line) {
		this.controllers.put(line, controller);
	}

	public LegacyControllerInterface getController(HttpServletRequest req) {
		return this.controllers
				.get(new RequestLine(req.getRequestURI(), RequestMethod.valueOf(req.getMethod().toUpperCase())));
	}

	public void initialize() {
		addController(new HomeController(), new RequestLine("/home", RequestMethod.GET));
		addController(new LoginController(), new RequestLine("/users/loginForm", RequestMethod.GET));
		addController(new UserJoinFormController(), new RequestLine("/users/form", RequestMethod.GET));
		addController(new LoginPostController(), new RequestLine("/users/login", RequestMethod.POST));
		addController(new CreateUserController(), new RequestLine("/users/create", RequestMethod.POST));
	}

}
