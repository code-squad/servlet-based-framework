package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
		initialize();
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public void initialize() {
		for (Class<?> clazz : preInstanticateBeans) {
			beans.put(clazz, instantiateClass(clazz));
		}
	}

	private Object instantiateClass(Class<?> targetClazz) {
		if (beans.containsKey(targetClazz)) {
			return beans.get(targetClazz);
		}
		targetClazz = BeanFactoryUtils.findConcreteClass(targetClazz, preInstanticateBeans);
		Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(targetClazz);
		Optional<Constructor<?>> optionalConstructor = Optional.ofNullable(constructor);
		try {
			constructor = optionalConstructor.isPresent() ? optionalConstructor.get() : targetClazz.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error(e.getMessage());
			logger.error("빈 생성 도중 에러가 발생하였다.");
		}
		Object bean = instantiateConstructor(constructor);
		return bean;
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		Arrays.asList(parameterTypes).stream().forEach(p -> {
			args.add(instantiateClass(p));
		});
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}

	public Map<Class<?>, Object> getControllers() {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		preInstanticateBeans.stream().filter(c -> c.isAnnotationPresent(Controller.class))
				.forEach(c -> controllers.put(c, beans.get(c)));
		return controllers;
	}
}
