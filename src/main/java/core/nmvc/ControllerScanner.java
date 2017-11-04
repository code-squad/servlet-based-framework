package core.nmvc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

		/*
		 * for (Class<?> c : annotated) { try { this.controllers.put(c,
		 * c.newInstance()); } catch (InstantiationException | IllegalAccessException e)
		 * { e.printStackTrace(); } }
		 */
		
		this.annotated.stream().forEach(c -> {
			try {
				this.controllers.put(c, c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		
		System.err.println("생성된 컨트롤러 인스턴스의 개수는 : " + this.controllers.size());
		
	}

	public Set<Class<?>> getAnnotatedClasses() {
		return this.annotated;
	}

	public Object getControllers(Class<?> targetClass) {
		return this.controllers.get(targetClass);
	}

}
