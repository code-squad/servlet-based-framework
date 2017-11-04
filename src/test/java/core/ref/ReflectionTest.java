package core.ref;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    }
    
    @Test
    public void useConstructorFromReflection() {
    		Class<Student> parsedClass = Student.class;
    		Student stu = null;
    		
    		try {
				stu = (Student) Arrays.asList(parsedClass.getDeclaredConstructors()).stream().findFirst().get().newInstance((Object[]) null);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e) {
				logger.error(e.getMessage());
			}
    		
    		assertTrue(stu instanceof Student);
    }
    
    @Test
    public void privateFieldAccess() {
    		Student stu = new Student();
    		Class<Student> parsedClass = Student.class;
    		
    		List<Field> fields = Arrays.asList(parsedClass.getDeclaredFields()).stream().collect(Collectors.toList());
    		
    		Field nameField = fields.stream().filter(f -> f.getName().equalsIgnoreCase("name"))
    		.findFirst().get();
    		
    		nameField.setAccessible(true);
    		try {
				nameField.set(stu, "재성");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
    		logger.debug(stu.getName());
    		assertEquals("재성", stu.getName());
    		
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
