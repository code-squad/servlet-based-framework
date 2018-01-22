package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import next.exception.ControllerException;

public class BeanFactory {

	private Set<Class<?>> preInstanticateBeans;
	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}
	public Map<Class<?>, Object> getTestBean() {
		return beans;
	}

	public void initialize() {
		for (Class<?> clazz : preInstanticateBeans) {
			beans.put(clazz, createNewInstance(clazz));
		}
	}

	private Object createNewInstance(Class<?> clazz) {
		try {
			Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
			if (constructor == null) {
				return clazz.newInstance();
			}
			Class<?>[] parameters = constructor.getParameterTypes();
			List<Object> args = new ArrayList<Object>();
			for (Class<?> parameter : parameters) {
				Object arg = createNewInstance(BeanFactoryUtils.findConcreteClass(parameter, preInstanticateBeans));
				args.add(arg);
			}
			return constructor.newInstance(args.toArray());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ControllerException(e);
		}
	}
}