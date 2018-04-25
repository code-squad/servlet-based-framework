package core.di.factory;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

import core.nmvc.Bean;
import core.nmvc.ClassPathBean;
import core.nmvc.ConfigurationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

public class BeanFactory { // 프레임워크의 bean 들을 설정해주는 클래스
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    //    private Set<Class<?>> beanCandidates;
    private Set<Bean> beanCandidates;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory() {
    }

    public BeanFactory(Set<Bean> beanCandidates) {
        this.beanCandidates = beanCandidates;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) Optional.ofNullable(this.beans.get(requiredType)).orElse(null);
    }

    // 프레임워크가 시작되면서 프레임워크가 관리해야 할 인스턴스들(= bean)을 생성하는 부분

    public void initialize() {
        // DI 실행
        this.beanCandidates.forEach(candidate -> {
            try {

                this.beans.put(candidate.getClazz(), setField(candidate));

            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        this.beans.forEach((clazz, object) -> logger.debug("beans : {}", clazz, object));
    }

    private Set<Class<?>> getClasses() {
        return this.beanCandidates.stream().map(Bean::getClazz).collect(Collectors.toSet());
    }

    private Bean find(Class<?> clazz) {
        return this.beanCandidates.stream().filter(b -> b.getClazz().equals(clazz)).findFirst().orElse(null);
    }

    private Object setField(Bean bean) throws IllegalAccessException, InstantiationException, InvocationTargetException {// 재귀로 구현
        if (bean instanceof ClassPathBean) {
            // 1. constructor 찾기
            ClassPathBean classPathBean = (ClassPathBean) bean;

            Constructor injectConstructor = classPathBean.getInjectedConstructor();
            if (injectConstructor == null) return classPathBean.getClazz().newInstance();

            Parameter[] params = injectConstructor.getParameters();
            List<Object> args = getObjects(params);
            return BeanUtils.instantiateClass(injectConstructor, args.toArray());
        }
        // 1. Method 찾기
        ConfigurationBean configBean = (ConfigurationBean) bean;
        Method method = configBean.getBeanMethod();
        Parameter[] params = method.getParameters();

        if (params.length == 0) return method.invoke(configBean.getConfigurationFile().newInstance());
        List<Object> args = getObjects(params);
        return method.invoke(configBean.getConfigurationFile().newInstance(), args.toArray());
    }

    private List<Object> getObjects(Parameter[] params) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> args = new ArrayList<>();
        for (Parameter p : params) {
            args.add(inject(p));
        }
        return args;
    }

    public Object inject(Parameter param) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (getClasses().contains(param.getType())) {
            // 인터페이스인데도 빈 후보로 등록된 경우
            if (param.getType().isInterface()) {
                ConfigurationBean configBean = (ConfigurationBean) find(param.getType());
                return configBean.getBeanMethod().invoke(configBean.getConfigurationFile().newInstance());
            }
            return setField(find(param.getType()));

        }
        // 인터페이스라서 후보로 등록되어있지 않은경우
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(param.getType(), this.beanCandidates);
        return setField(find(concreteClass));
    }
}
