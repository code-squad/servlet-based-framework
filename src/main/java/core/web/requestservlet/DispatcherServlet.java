package core.web.requestservlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import core.mvc.ModelAndView;
import core.nmvc.HandlerAdapter;
import core.nmvc.HandlerExecutionAdapter;
import core.nmvc.HandlerMapping;
import core.nmvc.HandlerMappingFactory;
import core.nmvc.LegacyControllerAdapter;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	
	private Set<HandlerMapping> mappers = Sets.newHashSet();
	private Set<HandlerAdapter> adapters = Sets.newHashSet();

	@Override
	public void init() {
		this.mappers = HandlerMappingFactory.getSets("core.mvc.controller");
		this.adapters.add(new HandlerExecutionAdapter());
		this.adapters.add(new LegacyControllerAdapter());

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Object controller = mappers.stream().filter(h -> h.getController(req) != null)
				.findFirst().get()
				.getController(req);
		try {
			ModelAndView mav = adapters.stream().filter(a -> a.supports(controller))
					.findFirst().get().run(controller,req, res);	
			mav.getView().render(mav.getModel(), req, res);
		} catch (Exception e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			log.error(e.getMessage());
		}
	}


}
