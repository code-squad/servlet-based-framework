package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
public interface LegacyController {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}