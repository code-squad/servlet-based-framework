package core.nmvc;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;

import java.util.Set;

class BeanScanner {
    private static Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    static Set<Class<?>> getControllers() {
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    static Set<Class<?>> getServices() {
        return reflections.getTypesAnnotatedWith(Service.class);
    }

    static Set<Class<?>> getRepositories() {
        return reflections.getTypesAnnotatedWith(Repository.class);
    }
}
