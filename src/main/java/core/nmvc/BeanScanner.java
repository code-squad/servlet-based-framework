package core.nmvc;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class BeanScanner {
	private static final Logger logger = LoggerFactory.getLogger(BeanScanner.class);
	private Reflections reflections;

	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	@SuppressWarnings("unchecked")
	public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
		Set<Class<?>> beans = Sets.newHashSet();
		for (Class<? extends Annotation> clazz : annotations) {
			logger.debug("clazz : {}", clazz);
			beans.addAll(reflections.getTypesAnnotatedWith(clazz));
		}
		return beans;
	}

}
