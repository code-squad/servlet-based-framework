package core.nmvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;

public class ControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
	private Reflections reflections = new Reflections("next.controller");
	
	ControllerScanner(){
		
	}
	ControllerScanner(Object packages){
		this.reflections = new Reflections(packages);
	}
	
	public Map<Class<?>, Object> getController() {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(annotated);
	}
	private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) {
		Map<Class<?>,Object> mapper = new HashMap<>();
		annotated.stream().forEach(clazz ->  Arrays.stream(clazz.getDeclaredMethods())
				.forEach(m -> {
			try {
				 mapper.put(clazz, clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}}));
		return mapper;
	}

}
