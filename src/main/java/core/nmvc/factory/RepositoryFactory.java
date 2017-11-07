package core.nmvc.factory;

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
	
	public int implementMethods() {
		this.umimplementedMethods.stream().forEach(m -> this.umimplementedMethods.add(m));
		
		
	}
	
	private void execute() {
		
	}
}
