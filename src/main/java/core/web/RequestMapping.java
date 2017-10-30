package core.web;

import java.util.Map;

import com.google.common.collect.Maps;

import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.ForwardController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.RedirectController;
import next.controller.UpdateUserController;

public class RequestMapping {
	Map<String, Controller> controllers = Maps.newHashMap();

	public 	RequestMapping(){
		init();
	}
	public void init() {
		controllers.clear();
		controllers.put("/users/create", new CreateUserController());
		controllers.put("/users/form", new CreateUserController());
		controllers.put("/user/login", new LoginController());
		controllers.put("/users/loginForm", new LoginController());
		controllers.put("/users/logout", new LogoutController());
		controllers.put("/users/profile", new ProfileController());
		controllers.put("/users", new ListUserController());
		controllers.put("/users/update", new UpdateUserController());
		controllers.put("/users/updateForm" , new UpdateUserController());
		controllers.put("", new HomeController());
	}
	
	public Controller getController(String path) {
		
//		if(controllers.get(path)!=null) {
			return controllers.get(path);
//		}
	}
}
