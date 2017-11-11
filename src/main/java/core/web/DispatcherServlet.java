package core.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import core.nmvc.HandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = { "", "/" }, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

	private LegacyHandlerMapping lhm;
	private AnnotationHandlerMapping ahm;

	private List<HandlerMapping> mappings = new ArrayList<>();
	private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();


	@Override
	public void init() {
		handlerAdapters.add(new ControllerHandlerAdapter());
		handlerAdapters.add(new HandlerExecutionHandlerAdapter());
		lhm = new LegacyHandlerMapping();
		lhm.initMapping();
		ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();

		mappings.add(lhm);
		mappings.add(ahm);
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object handler = getHandler(req);
		try {
			ModelAndView mav = execute(handler);
			if (mav != null) {
				mav.getView().render(mav.getModel(), req, resp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private ModelAndView execute(Object handler) throws Exception {
		return (ModelAndView) handlerAdapters.stream().filter(ha -> ha.supports(handler)).findFirst().get();
	}

	private Object getHandler(HttpServletRequest req) {
		return mappings.stream().map(hm -> hm.getHandler(req)).filter(h -> h != null).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));
	}

}