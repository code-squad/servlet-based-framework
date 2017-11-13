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
		//TODO 잠시 구현을 멈추고 있는 리포지토리 프레임워크를 계속 구현한다. 
		
		this.umimplementedMethods.stream().forEach(m -> this.umimplementedMethods.add(m));
		return 0;
	}
	
	private void execute() {
		
	}
}
