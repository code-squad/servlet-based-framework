package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final long serialVersionUID = 1L;
	private static final RequestMapping requestMapper = new RequestMapping();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("request method: " + req.getMethod() + " request uri : " + req.getRequestURI());
		try {
			Controller controller = requestMapper.getMatchController(new RequestMethod(req.getRequestURI(), req.getMethod()));
			controller = controller==null ? new ForwardController(req.getRequestURI()) : controller;
			String url = controller.execute(req, resp);
			sendStrategy sendStrategy = url.contains("redirect") ? new Redirect() : new Forward();
			sendStrategy.excuteSend(req, resp, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private interface sendStrategy {
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException;
	}

	private class Redirect implements sendStrategy {
		@Override
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException {
			int idx = url.indexOf(":");
			String redirectUrl = url.substring(idx + 1);
			resp.sendRedirect(redirectUrl);
		}
	}

	private class Forward implements sendStrategy {
		@Override
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException {
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);
		}
	}
}
