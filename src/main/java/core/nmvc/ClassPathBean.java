package core.nmvc;

import core.di.factory.BeanFactoryUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassPathBean extends Bean {

    ClassPathBean(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate(Bean bean, Set<Bean> beanCandidates) throws IllegalAccessException, InstantiationException, InvocationTargetException {
       return null;
    }

    public Constructor getInjectedConstructor(){
        return BeanFactoryUtils.getInjectedConstructor(super.clazz);
    }

}
