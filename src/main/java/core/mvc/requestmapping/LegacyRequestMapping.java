package core.mvc.requestmapping;

import java.util.HashMap;
import java.util.Map;

import core.annotation.RequestMethod;
import core.mvc.controller.Controller;
import core.mvc.controller.CreateUserController;
import core.mvc.controller.HomeController;
import core.mvc.controller.LoginController;
import core.mvc.controller.LoginPostController;
import core.mvc.controller.UserJoinFormController;

public class LegacyRequestMapping {

	private static LegacyRequestMapping rm;
	private Map<RequestLine, Controller> controllers = new HashMap<>();

	private LegacyRequestMapping() {

	}

	public static LegacyRequestMapping getInstance() {
		if (rm == null) {
			rm = new LegacyRequestMapping();
		}
		return rm;
	}

	public void addController(Controller controller, RequestLine line) {
		this.controllers.put(line, controller);
	}

	public Controller getController(RequestLine line) {
		return this.controllers.get(line);
	}

	public void initialize() {
		addController(new HomeController(), new RequestLine("/home", RequestMethod.GET));
		addController(new LoginController(), new RequestLine("/users/loginForm", RequestMethod.GET));
		addController(new UserJoinFormController(), new RequestLine("/users/form", RequestMethod.GET));
		addController(new LoginPostController(), new RequestLine("/users/login", RequestMethod.POST));
		addController(new CreateUserController(), new RequestLine("/users/create", RequestMethod.POST));
	}

}
