package core.web.requestservlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.controller.Controller;
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
		if (controller == null) {
			System.err.println("No Controllers Found.");
		}
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
