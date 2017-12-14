package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	AnswerDao answerDao = AnswerDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		String questionId = request.getParameter("questionId");
		String contents = request.getParameter("contents");
		HttpSession session = request.getSession();
		User user = UserSessionUtils.getUserFromSession(session);
		Answer answer = new Answer(getUserName(user), contents, Long.valueOf(questionId));

		log.debug("answer : {}", answer);

		Answer savedAnswer = answerDao.insert(answer);
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(savedAnswer));
		return null;
	}

	// 실습 편의성을 위해 로그인 안된 유저는 임시 유저라는 이름으로 댓글 작성"
	private String getUserName(User user) {
		if (user == null) {
			return "임시 유저";
		}
		return user.getName();
	}
}
