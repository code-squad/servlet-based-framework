package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanScanner {
	private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);

	private Map<Class<?>, Object> beans = Maps.newHashMap();
	private Set<Class<?>> annotated = Sets.newHashSet();
	private Reflections r;
	private Object[] basepackages;

	public BeanScanner(Object... basePackages) {
		this.basepackages = basePackages;
		scanBeans();
	}

	@SuppressWarnings("unchecked")
	private void scanBeans() {
		this.r = new Reflections(basepackages[0]);
		this.annotated = getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);

		this.annotated.stream().forEach(c -> {
			try {
				log.info("CURRENTLY SCANNING AN ANNOTATED BEANS : {}" , c.getName());
				this.beans.put(c, c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>...annotations) {
		Set<Class<?>> returnSet = Sets.newHashSet();
		for (Class<? extends Annotation> anno : annotations) {
			returnSet.addAll(this.r.getTypesAnnotatedWith(anno));
		}
		return returnSet;
	}

	public Set<Class<?>> getAnnotatedClasses() {
		return this.annotated;
	}

	public Object getControllersInstance(Class<?> targetClass) {
		return this.beans.get(targetClass);
	}

}
