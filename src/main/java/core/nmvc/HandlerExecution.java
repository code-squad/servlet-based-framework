package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public abstract class HandlerExecution {
    public abstract ModelAndView handle(HttpServletRequest request, HttpServletResponse response);
}