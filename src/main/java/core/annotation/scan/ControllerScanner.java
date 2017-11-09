package core.annotation.scan;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.exception.ControllerInstantiationException;


public class ControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
	public Map<Class<?>, Object> getController(){
		Reflections reflections = getReflections("next.controller");
		Set<Class<?>> annotated = getAnnotated(reflections);
		return InstantiateControllers(annotated);
	}
	
	public Map<Class<?>, Object> InstantiateControllers(Set<Class<?>> annotated) {
		return annotated.stream().collect(Collectors.toMap(a -> a, a -> {
			try { return a.newInstance(); } catch (Exception e) {
				throw new ControllerInstantiationException();
			}
		}));
	}
	
	public Reflections getReflections(String pacakge) {
		return new Reflections("next.controller");
	}
	
	public Set<Class<?>> getAnnotated(Reflections reflections){
		return reflections.getTypesAnnotatedWith(Controller.class);
	}
}
