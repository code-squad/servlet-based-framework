package next.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
	Map<RequestMethod, Controller> requestMap;
	public RequestMapping() {
		requestMap = new HashMap<RequestMethod, Controller>();
		initController();
	}
	public void initController() {
		requestMap.put(new RequestMethod("/users", "GET"), new ListUserController());
		requestMap.put(new RequestMethod("/users/loginForm", "GET"), new LoginFormController());
		requestMap.put(new RequestMethod("/users/logout", "GET"), new LogoutController());
		requestMap.put(new RequestMethod("/users/profile", "GET"), new ProfileController());
		requestMap.put(new RequestMethod("/users/updateForm", "GET"), new UpdateFormController());
		requestMap.put(new RequestMethod("/users/form", "GET"), new ForwardController("/user/form"));
		
		requestMap.put(new RequestMethod("/users/create", "POST"), new CreateUserController());
		requestMap.put(new RequestMethod("/users/login", "POST"), new LoginController());
		requestMap.put(new RequestMethod("/users/update", "POST"), new UpdateUserController());
		
	}
	public Controller getMatchController(RequestMethod requestMethod) {
		return this.requestMap.containsKey(requestMethod) ? this.requestMap.get(requestMethod) : null;
	}
}
