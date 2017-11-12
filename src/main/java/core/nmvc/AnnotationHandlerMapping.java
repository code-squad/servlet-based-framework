package core.nmvc;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping implements HandlerMapping {

	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		for (Object base : basePackage) {
			ControllerScanner cs = new ControllerScanner(base);
			Map<Class<?>, Object> mapper = cs.getController();
			mapper.keySet().stream().forEach(clazz -> Arrays.stream(clazz.getDeclaredMethods()).forEach(m -> {
				ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				HandlerKey handlerKey = createHandlerKey(rm);
				try {
					handlerExecutions.put(handlerKey, new HandlerExecution(m, clazz.newInstance()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}));
		}

	}

	@Override
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}

	private HandlerKey createHandlerKey(RequestMapping rm) {

		return new HandlerKey(rm.value(), rm.method());
	}

}
