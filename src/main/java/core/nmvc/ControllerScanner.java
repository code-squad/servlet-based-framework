package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {

    public static Set<Class<?>> getControllers(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

}
