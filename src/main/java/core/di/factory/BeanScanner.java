package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.exception.HandlerControllerInstantiationException;


public class BeanScanner {
	private Map<Class<?>, Object> annotationHandleMap = new HashMap<Class<?>, Object>();
	public Set<Class<?>> getAnnotationHandleKeySets(){
		return annotationHandleMap.keySet();		
	}
	public BeanScanner(Object[] obj) {
		Reflections reflections = new Reflections(obj);			
		List<Class<? extends Annotation>> annotationObjects = Arrays.asList(Controller.class, Service.class, Repository.class);
		annotationObjects.stream().forEach(annotation -> {
			instantiateControllers(reflections.getTypesAnnotatedWith(annotation));
		});
	}
	
	private void instantiateControllers(Set<Class<?>> annotated) {
		annotationHandleMap.putAll(annotated.stream().collect(Collectors.toMap(a -> a, a -> new HandlerControllerInstantiationException())));
	}
	
	public Object getControllerInstance(Class<?> clazz) {
		return annotationHandleMap.get(clazz);
	}
	
}
