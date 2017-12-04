package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
	private String path;

	public ForwardController(String path) {
		this.path = path + ".jsp";
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return path;
	}

}
