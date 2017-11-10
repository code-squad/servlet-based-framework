package next.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.mvc.ModelAndView;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.LegacyHandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;
	  
	@Override
	public void init() throws ServletException {
		lhm = new LegacyHandlerMapping();
		ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ModelAndView mav = lhm.getHandler(req).orElse(ahm.getHandler(req)
					.orElseGet(PageNotFoundHandlingController::new)).execute(req, resp);
			mav.getView().render(mav.getModel(), req, resp);
		} catch (Exception e) {
			throw new ServletException();
		}
	}
}
