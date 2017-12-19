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

	public ControllerScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInitiatedControllers);
	}

	public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) {
		Map<Class<?>, Object> classes = new HashMap<>();
		for (Class<?> clazz : annotated) {
			try {
				classes.put(clazz, clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("클래스 인스턴스 생성시 에러 발생");
				e.printStackTrace();
			}
		}
		return classes;
	}

}
