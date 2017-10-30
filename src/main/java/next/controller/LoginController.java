package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet(value = { "/users/login", "/users/loginForm" })
public class LoginController  implements Controller {
    private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("passworxd");
        User user = DataBase.findUserById(userId);
        if (user == null) {
        	request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
        	request.setAttribute("loginFailed", true);
        	return "/user/login.jsp";
        }
	}
}
