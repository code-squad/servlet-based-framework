package core.web.requestservlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.controller.Controller;
import core.mvc.controller.HomeController;
import core.mvc.controller.LoginController;
import core.mvc.requestmapping.RequestLine;
import core.mvc.requestmapping.RequestMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestMapping rm;


	@Override
	public void init() {
		System.out.println("hi. I just initialized a new RequestMapping instance! Yay!");
		this.rm = RequestMapping.getInstance();
		rm.initialize();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) {
		RequestLine requestedLine = generateRequestLine(req.getRequestURI(), RequestMethod.valueOf(req.getMethod()));
		Controller controller = rm.getController(requestedLine);

		try {
			ModelAndView mav = controller.run(req);
			mav.getView().render(mav.getModel(), req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RequestLine generateRequestLine(String path, RequestMethod method) {
		return new RequestLine(path, method);
	}


}
