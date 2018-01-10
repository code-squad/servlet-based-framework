package next.controller;

import java.util.HashMap;

public class RequestMapping {
	private HashMap<String, Controller> map = new HashMap<String, Controller>();

	public RequestMapping() {
		init();
	}

	public Controller mappingController(String url) {
		Controller controller = map.get(url);
		if (controller == null) {
			return new FilePathController(url);
		}
		return controller;
	}

	private void init() {
		map.put("/users/create", new CreateUserController());
		map.put("/users/form", new FilePathController("/user/form.jsp"));
		map.put("/", new HomeController());
		map.put("/users", new ListUserController());
		map.put("/users/login", new LoginController());
		map.put("/users/loginForm", new FilePathController("/user/login.jsp"));
		map.put("/users/logout", new LogoutController());
		map.put("/users/profile", new ProfileController());
		map.put("/users/update", new UpdateUserController());
		map.put("/users/updateForm", new UpdateUserFormController());
		map.put("/api/qna/addAnswer", new AddAnswerController());
		map.put("/api/qna/deleteAnswer", new DeleteAnswerController());
		map.put("/qna/show", new QuestionShowController());
	}
}