package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.Controller;

public class ControllerScanner {

	private Map<Class<?>, Object> controllers = Maps.newHashMap();
	private Set<Class<?>> annotated = Sets.newHashSet();
	private Object[] basepackages;

	public ControllerScanner(Object... basePackages) {
		this.basepackages = basePackages;
	}

	public void findControllers() {

		this.annotated = new Reflections(this.basepackages[0]).getTypesAnnotatedWith(Controller.class);

		this.annotated.stream().forEach(c -> {
			try {
				this.controllers.put(c, c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	public Set<Class<?>> getAnnotatedClasses() {
		return this.annotated;
	}

	public Object getControllersInstance(Class<?> targetClass) {
		return this.controllers.get(targetClass);
	}

}
