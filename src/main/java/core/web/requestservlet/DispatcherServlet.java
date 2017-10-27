package core.web.requestservlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMethod;
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

	private enum ResponseTypes {
		PAGE, REDIRECT;
	}

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

		String responseResource = controller.run(req);
		ReturnValue rv = new ReturnValue(responseResource, req, res);
		try {
			rv.send();
		} catch (IOException | ServletException e) {
			System.err.println(e.getMessage());
		}

	}

	private RequestLine generateRequestLine(String path, RequestMethod method) {
		return new RequestLine(path, method);
	}

	class ReturnValue {
		ResponseTypes type;
		String response;
		HttpServletRequest req;
		HttpServletResponse res;

		ReturnValue(String response, HttpServletRequest req, HttpServletResponse res) {
			if (response.contains("redirect:")) {
				this.type = ResponseTypes.REDIRECT;
			} else {
				this.type = ResponseTypes.PAGE;
			}
			this.response = response;
			this.req = req;
			this.res = res;
		}

		void send() throws IOException, ServletException {
			if (this.type == ResponseTypes.REDIRECT) {
				res.sendRedirect(this.response);
				return;
			}
			RequestDispatcher rd = req.getRequestDispatcher(this.response);
			rd.forward(req, res);
		}
	}

}
