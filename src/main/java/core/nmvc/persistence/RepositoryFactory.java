package core.nmvc.persistence;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import core.annotation.Repository;

public class RepositoryFactory {

	private Set<DataObject> daos;
	private Set<Class<?>> annotatedClasses;
	private Set<Class<?>> domains;
	private List<Method> umimplementedMethods;

	public int initialize() {
		annotatedClasses = new Reflections("next.model").getTypesAnnotatedWith(Repository.class);
		annotatedClasses.stream().forEach(c -> {
			this.umimplementedMethods = Arrays.asList(c.getMethods());
		});
		return this.annotatedClasses.size();
	}

	private void execute() {

	}
}
