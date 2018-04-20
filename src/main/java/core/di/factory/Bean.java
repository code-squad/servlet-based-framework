package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public abstract class Bean {
    private Type beanType;

    abstract Object setField(Type type) throws IllegalAccessException, InstantiationException, InvocationTargetException;
}
