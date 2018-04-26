package core.nmvc;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;

public abstract class Bean {
    Class<?> clazz;

    public Bean(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public abstract Object instantiate(List<Object> args) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    public abstract List<Parameter> getParameters();
}
