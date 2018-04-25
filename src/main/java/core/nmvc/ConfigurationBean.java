package core.nmvc;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigurationBean extends Bean {
    private Method beanMethod;
    private Class<?> configurationFile;

    ConfigurationBean(Class<?> clazz, Method beanMethod, Class<?> configurationFile) {
        super(clazz);
        this.beanMethod = beanMethod;
        this.configurationFile = configurationFile;
    }

    @Override
    public Object instantiate(Bean bean, Set<Bean> beanCandidates) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return null;
    }

    public Method getBeanMethod() {
        return beanMethod;
    }

    public Class<?> getConfigurationFile() {
        return configurationFile;
    }
}
