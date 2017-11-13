package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.di.exceptions.NoBeanException;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstantiateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstantiateBeans) {
		this.preInstantiateBeans = preInstantiateBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public void initialize() {
		this.preInstantiateBeans.stream()
				.forEach(b -> logger.debug("you have not-yet instantiated bean, named : {}", b.getName()));
		this.preInstantiateBeans.stream().forEach(c -> {
			instantiateClass(c);
		});
		logger.debug("number of beans : {} ", this.beans.size());
		this.beans.values().stream().forEach(b -> {
			logger.debug("name of created bean : {}", b.getClass().getName());
		});
	}

	public Map<Class<?>, Object> getControllers() {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		this.preInstantiateBeans.stream().filter(b -> b.isAnnotationPresent(Controller.class))
		.forEach(b -> controllers.put(b, this.beans.get(b)));
		return controllers;
	}

	private Object instantiateClass(Class<?> clazz) {

		if (clazz.isInterface()) {
			return this.beans.values().stream().filter(b -> clazz.isAssignableFrom(b.getClass())).findFirst()
					.orElse(instantiateClass(this.preInstantiateBeans.stream().filter(b -> clazz.isAssignableFrom(b))
							.findFirst().orElseThrow(() -> new NoBeanException(clazz))));
		}

		if (this.beans.containsKey(clazz)) {
			logger.debug("returns existing beans : {}", this.beans.get(clazz).getClass().getName());
			return this.beans.get(clazz);
		}

		if (BeanFactoryUtils.getInjectedConstructor(clazz) != null) {
			logger.debug("start recursion for class : {}", clazz.getName());
			this.beans.put(clazz, instantiateConstructor(BeanFactoryUtils.getInjectedConstructor(clazz)));
			return this.beans.get(clazz);
		}

		try {
			logger.debug("now instantiating : {} ", clazz.getName());
			this.beans.put(clazz, clazz.newInstance());
			return this.beans.get(clazz);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("something unexpected has occured : {} ", e.getMessage());
			return null;
		}
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		logger.debug("required arguments : {}", Arrays.asList(parameterTypes).toString());
		List<Object> args = Lists.newArrayList();

		Arrays.stream(parameterTypes).forEach(c -> args.add(instantiateClass(c)));

		logger.debug("size of arguments : {} ", args.size());
		logger.debug(args.toString());
		return BeanFactoryUtils.instantiateClass(constructor, args.toArray());
	}

}
