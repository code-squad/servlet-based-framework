package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.View;

public interface Controller {
	View execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
