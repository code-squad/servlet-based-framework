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

import core.annotation.RequestMethod;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final long serialVersionUID = 1L;
	private static RequestMapping requestMapper;

	public DispatcherServlet() {
		requestMapper = new RequestMapping();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("hello get");
		log.debug(req.getRequestURI() + " ");
		try {
			Controller controller = requestMapper.getController(RequestMethod.GET, req.getRequestURI());
			controller = controller==null ? new ForwardController(req.getRequestURI() + ".jsp") : controller;
			String url = controller.execute(req, resp);
			sendStrategy sendStrategy = url.contains("redirect") ? new Redirect() : new Forward();
			sendStrategy.excuteSend(req, resp, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("hello post");
		log.debug(req.getRequestURI() + " ");
		try {
			Controller controller = requestMapper.getController(RequestMethod.POST, req.getRequestURI());
			String url = controller.execute(req, resp);
			log.debug("post url : " + url);
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
