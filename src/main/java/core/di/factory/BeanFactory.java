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

import core.exception.BeanInstantiateException;

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
		preInstanticateBeans.stream().filter(c -> !beans.containsKey(c))
		.forEach(c -> { 
			beans.put(c, instantiateClass(c));
			logger.debug("bean create : " + c);
		});
	}

	private Object instantiateClass(Class<?> clazz) {
		if(beans.containsKey(clazz)) 
			return beans.get(clazz);
		Class<?> implementsClass = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
		Optional<Constructor<?>> cons = Optional.ofNullable(BeanFactoryUtils.getInjectedConstructor(implementsClass));
		try {
			return cons.isPresent() ?  instantiateConstructor(cons.get()) : implementsClass.newInstance();
		} catch (Exception e) {
			throw new BeanInstantiateException();
		}
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		Arrays.stream(parameterTypes).forEach(param -> {
			Object obj = instantiateClass(param);
			args.add(obj);
		});
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
}
