package next.controller;

import core.db.DataBase;
import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements CommonController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }
        UserDao userDao = new UserDao();

        request.setAttribute("users", userDao.findAll());
        return "/user/list.jsp";
    }
}
