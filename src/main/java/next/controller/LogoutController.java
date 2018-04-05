package next.controller;

import core.mvc.Controller;
import next.model.Response;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {
    private static final long serialVersionUID = 1L;

//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
//        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
//        return "redirect:/";
//    }


    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return Response.isNotAjax("redirect:/");
    }
}
