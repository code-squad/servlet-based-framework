package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class CreateAnswerController implements Controller {
	AnswerDao answerDao = AnswerDao.getInstance();
	QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String questionId = request.getParameter("questionId");
		Question question = questionDao.findByQuestionId(Long.valueOf(questionId));
		String contents = request.getParameter("contents");
		HttpSession session = request.getSession();
		User user = UserSessionUtils.getUserFromSession(session);
		Answer answer = new Answer(getUserName(user), contents, question.getQuestionId());
		answerDao.insert(answer);
		return "redirect:/qna/show?questionId=" + question.getQuestionId();
	}

	//실습 편의성을 위해 로그인 안된 유저는 임시 유저라는 이름으로 댓글 작성"
	private String getUserName(User user) {
		if (user == null) {
			return "임시 유저";
		}
		return user.getName();
	}

}
