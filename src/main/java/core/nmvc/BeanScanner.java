package core.nmvc;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;

import java.util.Set;

class BeanScanner {
    static Set<Class<?>> getControllers(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    static Set<Class<?>> getServices(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Service.class);
    }

    static Set<Class<?>> getRepositories(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Repository.class);
    }
}
