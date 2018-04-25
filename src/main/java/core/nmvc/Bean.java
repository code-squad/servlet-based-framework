package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class Bean {
    Class<?> clazz;

    public Bean(Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract Object instantiate(Bean bean, Set<Bean> beanCandidates) throws IllegalAccessException, InstantiationException, InvocationTargetException;


    public Class<?> getClazz() {
        return clazz;
    }
}
