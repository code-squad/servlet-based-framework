package core.annotation.scan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.exception.ControllerInstantiationException;


public class ControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
	public Map<Class<?>, Object> getController(){
		Reflections reflections = new Reflections("next.controller");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		return InstantiateControllers(annotated);
	}
	public Map<Class<?>, Object> InstantiateControllers(Set<Class<?>> annotated) {
		Map<Class<?>, Object> controllerMap = new HashMap<Class<?>, Object>();
		annotated.stream().forEach(a -> {
			try {
				controllerMap.put(a, a.newInstance());
			} catch (Exception e) {
				throw new ControllerInstantiationException();
			}
		});
		logger.debug("instanitate complete");
		return controllerMap;
	}
}
