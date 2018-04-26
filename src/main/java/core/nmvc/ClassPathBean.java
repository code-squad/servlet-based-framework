package core.nmvc;

import core.di.factory.BeanFactoryUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class ClassPathBean extends Bean {
    private Constructor injectConstructor;

    public ClassPathBean(Class<?> clazz) {
        super(clazz);
        this.injectConstructor = getInjectedConstructor();
    }

    public Constructor getInjectedConstructor() {
        return BeanFactoryUtils.getInjectedConstructor(super.clazz);
    }

    @Override
    public Object instantiate(List<Object> args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (this.injectConstructor == null) return this.getClazz().newInstance();
        return BeanUtils.instantiateClass(this.injectConstructor, args.toArray());
    }

    @Override
    public List<Parameter> getParameters() {
        return Arrays.asList(injectConstructor.getParameters());
    }

}
