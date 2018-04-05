package next.controller;

import core.db.DataBase;
import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.Response;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements Controller {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return Response.isNotAjax("redirect:/users/loginForm");
        }
        UserDao userDao = new UserDao();

        req.setAttribute("users", userDao.findAll());
        return Response.isNotAjax("/user/list.jsp");
    }
}
