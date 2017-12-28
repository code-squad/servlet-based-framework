package core.nmvc;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.HandlerMapping;

@SuppressWarnings("rawtypes")
public class AnnotationHandlerMapping implements HandlerMapping{
	private Object[] basePackage;
	private Map<HandlerKey, HandlerExecution> handlerExecutions;

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
		this.handlerExecutions = Maps.newHashMap();
	}

	public void initialize() {
		BeanScanner beanScanner = new BeanScanner(basePackage);
		Map<Class<?>, Object> allClass = beanScanner.getControllers();
		allClass.keySet().stream().forEach( clazz -> {
			ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))
			.forEach( method -> {
				RequestMapping rm = method.getAnnotation(RequestMapping.class);
				handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(allClass.get(clazz), method));
			});
		});
	}
	
	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}

	@Override
	public Optional<?> getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return Optional.ofNullable(handlerExecutions.get(new HandlerKey(requestUri, rm)));
	}
}
