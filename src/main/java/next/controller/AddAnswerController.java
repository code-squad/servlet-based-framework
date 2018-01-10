package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
import core.mvc.JsonView;
import core.mvc.ModelAndView;
=======
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController implements Controller {

	@Override
<<<<<<< HEAD
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
=======
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
		Answer answer = new Answer(request.getParameter("writer"), request.getParameter("contents"),
				Long.parseLong(request.getParameter("questionId")));

		AnswerDao answerDao = new AnswerDao();
		Long saveAnswerId = answerDao.insert(answer);
		Answer savedAnswer = answerDao.findById(saveAnswerId);
<<<<<<< HEAD
		ModelAndView mv = new ModelAndView(new JsonView(savedAnswer));
		mv.addObject(request);
		return mv;
	}
}
=======
		
		return ApiJsonMapper.mapping(response, savedAnswer);
	}

}
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
