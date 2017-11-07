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

	private void addController(RequestLine line, LegacyControllerInterface controller) {
		this.controllers.put(line, controller);
	}
	
	public void addControllerTest(RequestLine line, LegacyControllerInterface controller) {
		this.controllers.put(line, controller);
	}

	public LegacyControllerInterface getController(HttpServletRequest req) {
		return this.controllers
				.get(new RequestLine(req));
	}

	public void initialize() {
		addController(new RequestLine("/home", RequestMethod.GET), new HomeController());
		addController(new RequestLine("/users/loginForm", RequestMethod.GET), new LoginController());
		addController(new RequestLine("/users/form", RequestMethod.GET), new UserJoinFormController());
		addController(new RequestLine("/users/login", RequestMethod.POST), new LoginPostController());
		addController(new RequestLine("/users/create", RequestMethod.POST), new CreateUserController());
	}

}
