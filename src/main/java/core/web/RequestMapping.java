package core.web;

import java.util.Map;

import com.google.common.collect.Maps;

import next.controller.AddAnswerController;
import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.DeleteAnswerController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.ShowController;
import next.controller.ShowQuestionController;
import next.controller.UpdateUserController;

public class RequestMapping {
	private Map<String, Controller> controllers;

	public RequestMapping() {
		init();
	}

	public void init() {
		controllers = Maps.newHashMap();
		controllers.clear();
		controllers.put("/", new HomeController());
		controllers.put("/users/create", new CreateUserController());
		controllers.put("/users/form", new CreateUserController());
		controllers.put("/users/login", new LoginController());
		controllers.put("/users/loginForm", new LoginController());
		controllers.put("/users/logout", new LogoutController());
		controllers.put("/users/profile", new ProfileController());
		controllers.put("/users", new ListUserController());
		controllers.put("/users/update", new UpdateUserController());
		controllers.put("/users/updateForm", new UpdateUserController());
		controllers.put("/qna/show", new ShowController());
		controllers.put("/api/qna/addanswer", new AddAnswerController());
		controllers.put("/api/qna/deleteAnswer", new DeleteAnswerController());
		controllers.put("/api/questions", new ShowQuestionController());
	}

	public Controller getController(String path) {
		return controllers.get(path);
	}
}
