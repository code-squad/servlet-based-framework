package core.di.factory;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

import core.nmvc.ConfigurationBeanScanner;
import next.exception.NoReturnObjectMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class BeanFactory { // 프레임워크의 bean 들을 설정해주는 클래스
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;
    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private ConfigurationBeanScanner cbs;
    public BeanFactory(){}

    public BeanFactory(Set<Class<?>> preInstanticateBeans){
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    // 프레임워크가 시작되면서 프레임워크가 관리해야 할 인스턴스들(= bean)을 생성하는 부분

    public void initialize() {
        // DI 실행
        this.preInstanticateBeans.forEach(clazz -> {
            try {

                this.beans.put(clazz, setField(clazz));

            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        this.beans.forEach((clazz, object) -> logger.debug("beans : {}", clazz, object));
    }

    private Object setField(Class<?> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {// 재귀로 구현
        // 1. inject 생성자 가져오기
        Constructor injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (injectedConstructor == null) {
            return BeanFactoryUtils.findConcreteClass(clazz, this.preInstanticateBeans).newInstance();
        }

        //2. 생성자의 파라미터 목록
        List<Object> objects = getObjects(injectedConstructor.getParameters());

        // 3. 생성자 실행
        return injectedConstructor.newInstance(objects.toArray());
    }

    private List<Object> getObjects(Parameter[] parameters) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> objects = new ArrayList<>();
        for (Parameter p : parameters) {
            objects.add(setField(p.getType()));
        }
        return objects;
    }
}
