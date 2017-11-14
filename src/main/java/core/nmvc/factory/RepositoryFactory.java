package core.nmvc.factory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Repository;

public class RepositoryFactory {
	
	private Set<DataObject> daos;
	private Set<Class<?>> annotatedClasses;
	private Set<Class<?>> domains;
	private List<Method> umimplementedMethods;
	
	public int initialize(Object...objects) {
		initMultiPackages(objects);
		return this.umimplementedMethods.size();
	}
	
	public int implementMethods() {
		//TODO 잠시 구현을 멈추고 있는 리포지토리 프레임워크를 계속 구현한다. 
		
		this.umimplementedMethods.stream().forEach(m -> this.umimplementedMethods.add(m));
		return 0;
	}
	
	private void initMultiPackages(Object...objects) {
		this.annotatedClasses = Sets.newHashSet();
		Arrays.stream(objects).forEach(o -> this.annotatedClasses
				.addAll(new Reflections(o.toString()).getTypesAnnotatedWith(Repository.class)));
		this.annotatedClasses.stream().forEach(c -> this.umimplementedMethods = Arrays.asList(c.getMethods()));
	}
	
	private void execute() {
		
	}
}
