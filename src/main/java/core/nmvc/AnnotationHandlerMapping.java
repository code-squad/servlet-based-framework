package core.nmvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
	private ControllerScanner controllerScanner;

	private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		controllerScanner = new ControllerScanner(basePackage);
		Set<Class<?>> controllers = controllerScanner.getControllers();
		Set<Method> methods = createMethods(controllers);
		for (Method method : methods) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			HandlerKey hk = new HandlerKey(rm.value(), rm.method());
			HandlerExecution handlerExecution = new HandlerExecution(
					controllerScanner.instantiateControllers(method.getDeclaringClass()), method);
			handlerExecutions.put(hk, handlerExecution);
		}
	}

	public Set<Method> createMethods(Set<Class<?>> clazzs) {
		Set<Method> allMethods = new HashSet<Method>();
		for (Class<?> clazz : clazzs) {
			allMethods
					.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
		}
		return allMethods;
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		log.debug("getHandler(requestUri) : {}", requestUri);
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		log.debug("getHandler(method) : {}", request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
