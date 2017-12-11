package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.UserDao;
import next.model.User;

public class UpdateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);
	private UserDao userDao = UserDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userDao.findByUserId(request.getParameter("userId"));
		if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}

		User updateUser = new User(
				request.getParameter("userId"),
				request.getParameter("password"),
				request.getParameter("name"), 
				request.getParameter("email"));
		log.debug("Update User : {}", updateUser);
		userDao.update(updateUser);
		return "redirect:/";
	}

}
