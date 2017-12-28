package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.di.factory.BeanFactory;

public class BeanScanner {
	private static final Logger logger = LoggerFactory.getLogger(BeanScanner.class);
	private Reflections reflections;

	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
		BeanFactory beanFactory = new BeanFactory(preInitiatedControllers);
		beanFactory.initialize();
		return beanFactory.getControllers();
	}

}
