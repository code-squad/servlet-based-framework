package core.di.factory;

import java.util.Map;
import java.util.Set;

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
        this.preInstanticateBeans.forEach(BeanUtils::instantiateClass);
    }
}
