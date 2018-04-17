package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {// @Controller 가 붙은 클래스 찾아주는 클래스

    public static Set<Class<?>> getControllers(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

}
