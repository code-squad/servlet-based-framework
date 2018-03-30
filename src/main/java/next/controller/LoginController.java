package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (user == null) {
            request.setAttribute("loginFailed", true);
            // loginFailed 가 view 로 전달되는지 확인필요.
            return "/user/login.jsp";
        }
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }
        // 비밀번호 틀렸을 때
        request.setAttribute("loginFailed", true);
        return "user/login.jsp";
    }
}


