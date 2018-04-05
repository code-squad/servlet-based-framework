package next.controller;

import core.db.DataBase;
import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.Response;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userId = req.getParameter("userId");

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return Response.isNotAjax("/user/profile.jsp");
    }
}
