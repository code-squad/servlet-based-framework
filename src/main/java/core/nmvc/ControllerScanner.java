package core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import core.annotation.Controller;
import core.exception.HandlerControllerInstantiationException;


public class ControllerScanner {
	private Map<Class<?>, Object> controllerMap = new HashMap<Class<?>, Object>();
	public Set<Class<?>> getControllerKeySet(){
		return controllerMap.keySet();		
	}
	public ControllerScanner(Object[] obj) {
		Reflections reflections = new Reflections(obj);			
		instantiateControllers(reflections.getTypesAnnotatedWith(Controller.class));
	}
	
	private void instantiateControllers(Set<Class<?>> annotated) {
		controllerMap.putAll(annotated.stream().collect(Collectors.toMap(a -> a, a -> new HandlerControllerInstantiationException())));
	}

	public Object getControllerInstance(Class<?> clazz) {
		return controllerMap.get(clazz);
	}
	
}
