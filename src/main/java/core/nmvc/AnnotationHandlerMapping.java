package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.HandlerMapping;

public class AnnotationHandlerMapping implements HandlerMapping{
	private Object[] basePackage;
	private Map<HandlerKey, HandlerExecution> handlerExecutions;

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
		this.handlerExecutions = Maps.newHashMap();
	}

	public void initialize() {
		ControllerScanner controllerScanner = new ControllerScanner(basePackage);
		Map<Class<?>, Object> allClass = controllerScanner.getControllers();
		for (Class<?> clazz : allClass.keySet()) {
			Set<Method> allMethod = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
			putHandlerExecutions(allClass, clazz, allMethod);
		}
	}

	private void putHandlerExecutions(Map<Class<?>, Object> allClass, Class<?> clazz, Set<Method> allMethod) {
		for (Method method : allMethod) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(allClass.get(clazz), method));
		}
	}
	
	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}

	@Override
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
