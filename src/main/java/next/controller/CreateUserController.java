package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

public class CreateUserController implements Controller{
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getRequestURI().equals("/users/form")) {
			return "/user/form.jsp";
		}
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
        		request.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
		return "redirect:/";
	}
}
