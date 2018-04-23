package core.nmvc;

import core.annotation.Bean;
import core.di.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigurationBeanScanner {
    private Class<?> configurationFile;

    public void register(Class<?> configurationFile){// 어떤 설정파일 사용할 건지 설정
        this.configurationFile = configurationFile;
    }

    public Set<Method> findMethods(){
        return Arrays.stream(this.configurationFile.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Bean.class)).collect(Collectors.toSet());
    }

    public Class<?> getConfigurationFile() {
        return configurationFile;
    }
}
