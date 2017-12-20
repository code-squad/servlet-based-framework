package core.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import next.controller.Controller;
import next.controller.json.AddAnswerController;
import next.controller.json.DeleteAnswerController;
import next.controller.jsp.CreateQuestionController;
import next.controller.jsp.CreateUserController;
import next.controller.jsp.ForwardController;
import next.controller.jsp.HomeController;
import next.controller.jsp.LoginController;
import next.controller.jsp.ProfileController;
import next.controller.jsp.ShowQuestionController;
import next.controller.jsp.UpdateUserController;
import next.controller.jsp.UpdateUserFormController;

@SuppressWarnings("rawtypes")
public class LegacyHandlerMapping implements HandlerMapping {
	private Map<String, Controller> controllers;
	private static final LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();

	private LegacyHandlerMapping() {
		controllers = initControllers();
	}

	public static LegacyHandlerMapping getInstance() {
		return legacyHandlerMapping;
	}

	private static Map<String, Controller> initControllers() {
		Map<String, Controller> controllerMap = new HashMap<>();
		controllerMap.put("/users/create", new CreateUserController());
		controllerMap.put("/users/form", new ForwardController("/user/form"));
		controllerMap.put("/", new HomeController());
		controllerMap.put("/users/login", new LoginController());
		controllerMap.put("/users/loginForm", new ForwardController("/user/login"));
		controllerMap.put("/users/profile", new ProfileController());
		controllerMap.put("/users/update", new UpdateUserController());
		controllerMap.put("/users/updateForm", new UpdateUserFormController());
		controllerMap.put("/qna/show", new ShowQuestionController());
		controllerMap.put("/qna/create", new CreateQuestionController());
		controllerMap.put("/api/qna/addanswer", new AddAnswerController());
		controllerMap.put("/api/qna/deleteanswer", new DeleteAnswerController());
		return controllerMap;
	}

	@Override
	public Optional<?> getHandler(HttpServletRequest request) {
		return Optional.ofNullable(controllers.get(request.getRequestURI()));
	}


}
