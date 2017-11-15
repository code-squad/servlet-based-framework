package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

	public void initialize() {
		preInstanticateBeans.stream().forEach(c -> putBeans(c));
	}

	private void putBeans(Class<?> c) {
		try {
			if (instanticate(c) != null) {
				beans.put(c, instanticate(c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Object instanticate(Class<?> clazz) throws Exception {
		if (getBean(clazz) != null) {
			return clazz;
		}
		return instantiateClass(clazz);
	}

	private Object instantiateClass(Class<?> clazz) throws Exception {
		Constructor<?> injectedConstructors = BeanFactoryUtils.getInjectedConstructor(clazz);
		if (injectedConstructors == null) {
			return clazz.newInstance();
		}
		return instantiateConstructor(BeanFactoryUtils.getInjectedConstructor(clazz));
	}

	private Object instantiateConstructor(Constructor<?> constructor) throws Exception {

		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : parameterTypes) {
			if (getBean(instanceOfConcreteClass(clazz)) == null) {
				putBeans(instanceOfConcreteClass(clazz));
			}
			Object bean = getBean(instanceOfConcreteClass(clazz));
			args.add(bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}

	private Class<?> instanceOfConcreteClass(Class<?> clazz) {
		return BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
	}

}
