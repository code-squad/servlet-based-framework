package next.controller;

import core.mvc.CommonController;
import core.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// The controller that only handle simple view changing function with no such special logic.
public class FowardController implements CommonController {
    private String path;

    public FowardController(String path) {
        this.path = path;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (this.path.equals("/users/form")) return "/user/form.jsp";
        if (this.path.equals("/users/loginForm")) return "/user/login.jsp";
        if (this.path.equals("/qna/form")) return "/qna/form.jsp";
        return null;
    }
}
