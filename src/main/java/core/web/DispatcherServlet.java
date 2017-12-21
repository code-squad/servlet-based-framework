package core.web;

import java.io.IOException;
import java.util.ArrayList;
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

import core.mvc.ModelAndView;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.ControllerHandlerAdapter;
import core.nmvc.HandlerAdapter;
import core.nmvc.HandlerExecutionHandlerAdapter;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	@SuppressWarnings("rawtypes")
	private List<HandlerMapping> mappings = new ArrayList<>();
	private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
	
	@Override
	public void init() throws ServletException {
		LegacyHandlerMapping lhm = LegacyHandlerMapping.getInstance();
		AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("core.nmvc", "next.controller.jsp");
		ahm.initialize();
		
		mappings.add(lhm);
		mappings.add(ahm);
		
		handlerAdapters.add(new ControllerHandlerAdapter());
		handlerAdapters.add(new HandlerExecutionHandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object handler = getHandler(req);
		try {
			ModelAndView mav = execute(handler, req, resp).get();
			mav.render(req, resp);
		} catch (Exception e) {
			logger.error("service errrrrrr");
			logger.error(e.getMessage());
		}
	}
	
	private Optional<ModelAndView> execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return Optional.ofNullable(
								handlerAdapters.stream().
								filter(ha -> ha.supports(handler)).
								findFirst().get()
								.handle(req, resp, handler));
	}
	  
  private Object getHandler(HttpServletRequest req) {
      return mappings.stream()
          .map(hm -> hm.getHandler(req))
          .filter(h -> h.isPresent())
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."))	
          .get();
  }

}
