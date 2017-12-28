package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
		clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
		Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		if ( constructor == null ) {
			try {
				constructor = clazz.getConstructor();
				Object newClass = constructor.newInstance();
				beans.put(clazz, newClass);
				return newClass;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		Object newInstance =  instantiateConstructor(constructor);
		beans.put(clazz, newInstance);
		return newInstance;
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : parameterTypes) {
			clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
			Object param = beans.get(clazz);
			if ( param == null ) {
				instantiateClass(clazz);
				param = beans.get(clazz);
			}
			args.add(param);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
}
