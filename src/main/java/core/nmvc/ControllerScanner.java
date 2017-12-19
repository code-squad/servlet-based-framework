package core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;

public class ControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
	private Reflections reflections;
//	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);

	public ControllerScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() throws InstantiationException, IllegalAccessException {
		Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInitiatedControllers);
	}

	public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, Object> classes = new HashMap<>();
		for (Class<?> clazz : annotated) {
			classes.put(clazz, clazz.newInstance());
		}
		return classes;
	}

}
