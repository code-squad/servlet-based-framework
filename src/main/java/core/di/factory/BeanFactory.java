package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
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

	private Object instantiateClass(Class<?> clazz) {
		if (beans.containsKey(clazz)) {
			return beans.get(clazz);
		}
		Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		if (constructor == null) {
			try {
				constructor = clazz.getConstructor();
				Object bean = constructor.newInstance();
				beans.put(clazz, bean);
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		Object bean = instantiateConstructor(constructor);
		beans.put(clazz, bean);
		return bean;
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : parameterTypes) {
			clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
			Object bean = beans.get(clazz);
			args.add(bean == null ? instantiateClass(clazz) : bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
	
	public Map<Class<?>, Object> getControllers() {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		for (Class<?> clazz : preInstanticateBeans) {
			if (clazz.isAnnotationPresent(Controller.class)) {
				controllers.put(clazz, beans.get(clazz));
			}
		}
		return controllers;
	}
}
