package core.web;

import java.util.HashMap;
import java.util.Map;

import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.CreateUserFormController;
import next.controller.ForwardController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LoginFormController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;

public class RequestMapping {
	private Map<String, Controller> controllers;

	private RequestMapping() {
		controllers = initControllers();
	}

	public static RequestMapping createRequestMapping() {
		return new RequestMapping();
	}

	private static Map<String, Controller> initControllers() {
		Map<String, Controller> controllerMap = new HashMap<>();
		controllerMap.put("/users/create", new CreateUserController());
		controllerMap.put("/users/form", new CreateUserFormController());
		controllerMap.put("", new HomeController());
		controllerMap.put("/users", new ListUserController());
		controllerMap.put("/users/login", new LoginController());
		controllerMap.put("/users/loginForm", new LoginFormController());
		controllerMap.put("/users/profile", new ProfileController());
		controllerMap.put("/users/update", new UpdateUserController());
		controllerMap.put("/users/updateForm", new UpdateUserFormController());
		return controllerMap;
	}

	public Controller findController(String path) {
		Controller controller = controllers.get(path);
		if (controller == null) {
			return new ForwardController(path);
		}
		return controllers.get(path);
	}

}
