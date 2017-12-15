package next.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	private AnswerDao answerDao = AnswerDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		Answer answer = createAnswer(request);
		log.debug("answer : {}", answer);
		Answer savedAnswer = answerDao.insert(answer);
		
//		request.setAttribute("answer", savedAnswer);
		ModelAndView mav = new ModelAndView(new JsonView());
		mav.setAttribute("answer", savedAnswer);
		return mav;
	}
	
	private Answer createAnswer(HttpServletRequest request) {
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		String contents = request.getParameter("contents");
		HttpSession session = request.getSession();
		User user = UserSessionUtils.getUserFromSession(session);
		return new Answer(getUserName(user), contents, questionId);
	}

	// 실습 편의성을 위해 로그인 안된 유저는 임시 유저라는 이름으로 댓글 작성"
	private String getUserName(User user) {
		if (user == null) {
			return "임시 유저";
		}
		return user.getName();
	}
}
