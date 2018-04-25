package core.nmvc;

import core.di.factory.BeanFactoryUtils;

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
    public Object instantiate(Class<?> clazz, Set<Bean> beanCandidates) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanCandidates);
        Constructor injectedConstructor = getInjectedConstructor(concreteClass);
        if (injectedConstructor == null) {
            return concreteClass.newInstance();
        }
        //2. 생성자의 파라미터 목록
        List<Object> objects = new ArrayList<>();
        Parameter[] params = injectedConstructor.getParameters();
        for(Parameter p : params){
            objects.add(instantiate(p.getType(), beanCandidates));
        }
        // 3. 생성자 실행
        return injectedConstructor.newInstance(objects.toArray());
    }

    private Constructor getInjectedConstructor(Class clazz){
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }

}
