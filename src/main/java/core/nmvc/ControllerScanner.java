package core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import core.annotation.Controller;
import next.exception.ControllerException;

public class ControllerScanner {
	private Map<Class<?>, Object> controllers = new HashMap<Class<?>, Object>();
	private Reflections reflections;
	private Set<Class<?>> annotated;

	public ControllerScanner(Object[] basePackage) {
		try {
			reflections = new Reflections(basePackage);
			annotated = reflections.getTypesAnnotatedWith(Controller.class);
			for (Class<?> clazz : annotated) {
				controllers.put(clazz, clazz.newInstance());
			}
		} catch (InstantiationException e) {
			throw new ControllerException(e);
		} catch (IllegalAccessException e) {
			throw new ControllerException(e);
		}
	}

	public Set<Class<?>> getControllers() {
		return annotated;
	}

	public Object instantiateControllers(Class<?> clazz) {
		return controllers.get(clazz); 
	}
}
