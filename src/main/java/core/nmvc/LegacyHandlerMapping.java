package core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import next.controller.AddAnswerController;
import next.controller.QuestionController;
import next.controller.DeleteAnswerController;
import next.controller.ForwardController;
import next.controller.LegacyController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.QnaShowController;
import next.controller.RequestMethod;
import next.controller.UpdateFormController;

public class LegacyHandlerMapping implements HandlerMapping {
	private static final Map<RequestMethod, LegacyController> requestMap;
	static {
		requestMap = new HashMap<RequestMethod, LegacyController>();
		requestMap.put(new RequestMethod("/users", "GET"), new ListUserController());
		requestMap.put(new RequestMethod("/users/loginForm", "GET"), new ForwardController("/user/login"));
		requestMap.put(new RequestMethod("/users/logout", "GET"), new LogoutController());
		requestMap.put(new RequestMethod("/users/profile", "GET"), new ProfileController());
		requestMap.put(new RequestMethod("/users/updateForm", "GET"), new UpdateFormController());
		requestMap.put(new RequestMethod("/users/form", "GET"), new ForwardController("/user/form"));
		requestMap.put(new RequestMethod("/qna/show", "GET"), new QnaShowController());

		requestMap.put(new RequestMethod("/api/qna/deleteAnswer", "POST"), new DeleteAnswerController());
		requestMap.put(new RequestMethod("/api/qna/addanswer", "POST"), new AddAnswerController());
		requestMap.put(new RequestMethod("/users/login", "POST"), new LoginController());
	}

	@Override
	public Optional<LegacyController> getHandler(HttpServletRequest request) {
		RequestMethod rm = new RequestMethod(request.getRequestURI(), request.getMethod());
		return Optional.ofNullable(requestMap.get(rm));
	}
}
