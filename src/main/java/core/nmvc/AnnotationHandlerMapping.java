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

	@SuppressWarnings("unchecked")
	public void initialize() {

		ControllerScanner cs = new ControllerScanner(this.basePackage);
		cs.findControllers();

		Set<Class<?>> controllerClasses = cs.getAnnotatedClasses();

		for (Class<?> c : controllerClasses) {
			Set<Method> annotatedMethods = ReflectionUtils.getAllMethods(c,
					ReflectionUtils.withAnnotation(RequestMapping.class));
			for (Method m : annotatedMethods) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);

				this.handlerExecutions.put(new HandlerKey(rm.value(), rm.method()), new HandlerExecution(c, m));
			}
		}
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
