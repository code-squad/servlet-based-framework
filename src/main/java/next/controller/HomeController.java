package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class HomeController implements Controller {
    private static final long serialVersionUID = 1L;


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "index.jsp";
	}
}
