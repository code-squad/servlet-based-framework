package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
	private RequestMapping requestMapping = new RequestMapping();

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		log.debug("requestUrl : {}", ((HttpServletRequest) req).getRequestURI().toString());
		String uri = ((HttpServletRequest) req).getRequestURI();
		Controller controller = requestMapping.mappingController(uri);
		try {
			String url = controller.execute((HttpServletRequest) req, (HttpServletResponse) res);
			if (url.startsWith("redirect")) {
				String value[] = url.split(":");
				log.debug("redirect: {}", value[1]);
				((HttpServletResponse) res).sendRedirect(value[1]);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(url);
				log.debug("forward: {}", url);
				rd.forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}