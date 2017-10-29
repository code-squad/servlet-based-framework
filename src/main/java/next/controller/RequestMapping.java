package next.controller;

import java.util.HashMap;
import java.util.Map;

import core.annotation.RequestMethod;

public class RequestMapping {
	Map<RequestMethod, Map<String, Controller>> requestMap;
	public RequestMapping() {
		requestMap = new HashMap<RequestMethod, Map<String, Controller>>();
		initGetController();
		initPostController();
	}
	public void initGetController() {
		Map<String, Controller> controllerMap = new HashMap<String, Controller>();
		controllerMap.put("/users", new ListUserController());
		controllerMap.put("/users/loginForm", new LoginFormController());
		controllerMap.put("/users/logout", new LogoutController());
		controllerMap.put("/users/profile", new ProfileController());
		controllerMap.put("/users/updateForm", new UpdateFormController());
		requestMap.put(RequestMethod.GET, controllerMap);
	}
	public void initPostController() {
		Map<String, Controller> controllerMap = new HashMap<String, Controller>();
		controllerMap.put("/users/create", new CreateUserController());
		controllerMap.put("/users/login", new LoginController());
		controllerMap.put("/users/update", new UpdateUserController());
		requestMap.put(RequestMethod.POST, controllerMap);
	}
	public Controller getController(RequestMethod method, String url) {
		Map<String, Controller> controllerMap = this.requestMap.get(method);
		return controllerMap.containsKey(url) ? controllerMap.get(url) : null;
	}
}
