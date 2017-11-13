package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        Field[] f = clazz.getDeclaredFields();
        logger.debug("필드 추우ㄹ력!!");
        for (Field field : f) {
			logger.debug(field.getName());
		}
        Constructor[] c = clazz.getDeclaredConstructors();
        logger.debug("생성자 출력 !!");
        for (Constructor constructor : c) {
			logger.debug(constructor.getName());
		}
        logger.debug("메서드 출력!!!!!!!!!!!!!!!!!!!!!!!!");
        Method[] m = clazz.getMethods();
        for (Method method : m) {
        	logger.debug(method.getName());
			
		}
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }
}
