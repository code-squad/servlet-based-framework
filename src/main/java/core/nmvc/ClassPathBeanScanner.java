package core.nmvc;

import core.annotation.*;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassPathBeanScanner {
    private Object[] basePackage;

    public ClassPathBeanScanner(Object... basePackage) {
        this.basePackage = basePackage;
    }

    Set<Bean> getControllers() {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Controller.class).stream()
                .map(ClassPathBean::new).collect(Collectors.toSet());
    }

    private Set<Bean> getServices() {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Service.class).stream()
                .map(ClassPathBean::new).collect(Collectors.toSet());
    }

    private Set<Bean> getRepositories() {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Repository.class).stream()
                .map(ClassPathBean::new).collect(Collectors.toSet());
    }

    Set<Bean> doScan() {
        return Stream.concat(
                Stream.concat(getControllers().stream(), getServices().stream()),
                getRepositories().stream()).collect(Collectors.toSet());
    }


}
