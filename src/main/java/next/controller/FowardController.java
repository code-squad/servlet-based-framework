package next.controller;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.model.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// The controller that only handle simple view changing function with no such special logic.
public class FowardController implements Controller {
    private String path;

    public FowardController(String path) {
        this.path = path;
    }

    @Override
    public View execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (this.path.equals("/users/form")) return new JspView("/user/form.jsp");
        if (this.path.equals("/users/loginForm")) return new JspView("/user/login.jsp");
        if (this.path.equals("/qna/form")) return new JspView("/qna/form.jsp");
        return null;
    }
}
