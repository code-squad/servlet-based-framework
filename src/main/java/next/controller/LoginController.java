package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class LoginController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		log.debug("login controller start");
		String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            ModelAndView mav = new ModelAndView(new JspView("/user/login.jsp"));
            mav.addObject("loginFailed", true);
            return mav;
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            log.debug("login success");
            return new ModelAndView(new JspView("redirect:/"));
        } else {
            ModelAndView mav = new ModelAndView(new JspView("/user/login.jsp"));
            mav.addObject("loginFailed", true);
            return mav;
        }
	}
}
