package core.nmvc;

import core.annotation.Bean;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigurationBeanScanner {
    private Class<?> configurationFile;
    private Set<Method> beanMethods;

    public ConfigurationBeanScanner(Class<?> configurationFile) {
        this.configurationFile = configurationFile;
        this.beanMethods = getBeanMethods();
    }

    private Set<Method> getBeanMethods(){
        return Arrays.stream(this.configurationFile.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Bean.class)).collect(Collectors.toSet());
    }

     Set<core.nmvc.Bean> doScan(){
        return this.beanMethods.stream().map(method -> new ConfigurationBean(method.getReturnType(), method, this.configurationFile)).collect(Collectors.toSet());
    }

     Class<?> getConfigurationFile() {
        return configurationFile;
    }
}
