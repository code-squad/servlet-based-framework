package core.nmvc;

import core.di.factory.BeanFactoryUtils;

import java.lang.reflect.Constructor;

public class ClassPathBean extends Bean {

    ClassPathBean(Class<?> clazz) {
        super(clazz);
    }

    public Constructor getInjectedConstructor(){
        return BeanFactoryUtils.getInjectedConstructor(super.clazz);
    }

}
