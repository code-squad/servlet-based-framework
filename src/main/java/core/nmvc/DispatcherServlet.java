package core.nmvc;

import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import core.exception.DispatcherServletException;
import core.mvc.ModelAndView;
import next.controller.HomeController;
import next.controller.PageNotFoundHandlingController;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private static final long serialVersionUID = 1L;
	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;
	private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
	  
	@Override
	public void init() throws ServletException {
		lhm = new LegacyHandlerMapping();
		ahm = new AnnotationHandlerMapping();
		ahm.initialize();
		handlerAdapters.add(new ControllerHandlerAdapter());
	    handlerAdapters.add(new HandlerExecutionHandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp){
		Object handler = lhm.getHandler(req).
				orElse(ahm.getHandler(req).orElseGet(PageNotFoundHandlingController::new));
		try {
			getModelAndViewInHandlerAdapter(req, resp, handler)
			.filter(m -> m!=null).get().render(req, resp);
		} catch (Exception e) {
			throw new DispatcherServletException();
		}
	}
	
	public Optional<ModelAndView> getModelAndViewInHandlerAdapter(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
			return Optional.ofNullable(handlerAdapters.stream().filter(h -> h.supports(handler))
					.findFirst().get().handle(req, resp, handler));
	}
}
