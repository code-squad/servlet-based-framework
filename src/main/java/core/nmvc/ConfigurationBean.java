package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;


public class ConfigurationBean extends Bean {
    private Method beanMethod;
    private Class<?> configurationFile;

    ConfigurationBean(Class<?> clazz, Method beanMethod, Class<?> configurationFile) {
        super(clazz);
        this.beanMethod = beanMethod;
        this.configurationFile = configurationFile;
    }

    public Method getBeanMethod() {
        return this.beanMethod;
    }

    @Override
    public Object instantiate(List<Object> args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (getParameters().size() == 0) return this.beanMethod.invoke(this.configurationFile.newInstance());
        return beanMethod.invoke(this.configurationFile.newInstance(), args.toArray());
    }

    @Override
    public List<Parameter> getParameters(){
        return Arrays.asList(this.beanMethod.getParameters());
    }
}
