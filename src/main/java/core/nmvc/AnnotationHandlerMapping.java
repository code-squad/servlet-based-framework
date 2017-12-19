package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() throws InstantiationException, IllegalAccessException {
		ControllerScanner controllerScanner = new ControllerScanner(basePackage);
		Map<Class<?>, Object> classes = controllerScanner.getControllers();
		for (Class<?> key : classes.keySet()) {
			Set<Method> methods = ReflectionUtils.getAllMethods(key, ReflectionUtils.withAnnotation(RequestMapping.class));
			for (Method method : methods) {
				RequestMapping rm = method.getAnnotation(RequestMapping.class);
				handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(key, method));
			}
		}
	}
	
	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
