package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

public class BeanFactory { // 프레임워크의 bean 들을 설정해주는 클래스
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();
    // 생성자를 통해 bean 이 될 클래스들을 설정해주고 있다.
    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    // 프레임워크가 시작되면서 클래스의 인스턴스들(= bean)을 생성하는 부분
    public void initialize() {
        // 기본 생성자로 먼저 빈으로 등록
        this.preInstanticateBeans.forEach(bean ->
            this.beans.put(bean, BeanUtils.instantiateClass(bean)));
        // DI 실행
        this.beans.forEach((clazz, object) -> {
            try {
                // overwrite
                logger.debug("class, object : {}",  clazz, object);
                this.beans.put(clazz, setField(clazz));
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

    }

    private Object setField(Class<?> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {// 재귀로 구현
        // 1. inject 생성자 가져오기
        Constructor injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        // 원래 있던 빈 반환
        if(injectedConstructor == null) {
            Class concreteClass = BeanFactoryUtils.findConcreteClass(clazz, this.preInstanticateBeans);
            return this.beans.get(concreteClass);
        }

        //2. 생성자의 파라미터 목록
        Parameter[] parameters = injectedConstructor.getParameters();
        List<Object> objects = new ArrayList<>();
        for(Parameter p : parameters) {
            objects.add(setField(p.getType()));
        }
        // 3. 생성자 실행
        return injectedConstructor.newInstance(objects.toArray());
    }
}
