package core.nmvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping implements HandlerMapping {
	private final static Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
	
	private Object[] basePackage;
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		ControllerScanner cs = new ControllerScanner(this.basePackage);
		cs.findControllers();
		cs.getAnnotatedClasses().stream().forEach(c -> {
			ReflectionUtils.getAllMethods(c, ReflectionUtils.withAnnotation(RequestMapping.class)).stream()
					.forEach(m -> {
						RequestMapping rm = m.getAnnotation(RequestMapping.class);
						this.handlerExecutions.put(new HandlerKey(rm.value(), rm.method()), new HandlerExecution(c, m));
					});

		});
		log.debug("count of indexed controllers : " + cs.getAnnotatedClasses().size());
	}

	@Override
	public HandlerExecution getController(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.getMethodEnum(request);
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
