package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanScanner {
	private Set<Class<?>> annotationHandleSet = Sets.newHashSet();
	public Set<Class<?>> getAnnotationHandleKeySets(){
		return annotationHandleSet;	
	}
	public BeanScanner(Object[] obj) {
		Reflections reflections = new Reflections(obj);			
		List<Class<? extends Annotation>> annotationObjects = Arrays.asList(Controller.class, Service.class, Repository.class);
		annotationObjects.stream().forEach(annotation -> {
			instantiateControllers(reflections.getTypesAnnotatedWith(annotation));
		});
	}
	
	private void instantiateControllers(Set<Class<?>> annotated) {
		annotationHandleSet.addAll(annotated.stream().collect(Collectors.toSet()));
	}
	
}
