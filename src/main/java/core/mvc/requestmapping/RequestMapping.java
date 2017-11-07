package core.mvc.requestmapping;

import java.util.HashMap;
import java.util.Map;

import core.annotation.RequestMethod;
import core.mvc.controller.CreateUserController;
import core.mvc.controller.HomeController;
import core.mvc.controller.LegacyControllerInterface;
import core.mvc.controller.LoginController;
import core.mvc.controller.LoginPostController;
import core.mvc.controller.UserJoinFormController;

public class RequestMapping {

	private static RequestMapping rm;
	private Map<RequestLine, LegacyControllerInterface> controllers = new HashMap<>();

	private RequestMapping() {

	}

	public static RequestMapping getInstance() {
		if (rm == null) {
			rm = new RequestMapping();
		}
		return rm;
	}

	public void addController(LegacyControllerInterface controller, RequestLine line) {
		this.controllers.put(line, controller);
	}

	public LegacyControllerInterface getController(RequestLine line) {
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
