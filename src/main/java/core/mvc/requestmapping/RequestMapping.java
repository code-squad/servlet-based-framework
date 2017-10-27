package core.mvc.requestmapping;

import java.util.HashMap;
import java.util.Map;

import core.annotation.RequestMethod;
import core.mvc.controller.Controller;
import core.mvc.controller.HomeController;
import core.mvc.controller.LoginController;;

public class RequestMapping {

	private static RequestMapping rm;

	private RequestMapping() {

	}

	public static RequestMapping getInstance() {
		if (rm == null) {
			rm = new RequestMapping();
		}
		return rm;
	}

	private Map<RequestLine, Controller> controllers = new HashMap<>();
	

	public void addController(Controller controller, RequestLine line) {
		this.controllers.put(line, controller);
	}

	public Controller getController(RequestLine line) {
		return this.controllers.get(line);
	}
	
	public void initialize() {
		addController(new HomeController(), new RequestLine("/home", RequestMethod.GET));
		addController(new LoginController(), new RequestLine("/login", RequestMethod.GET));
	}

}
