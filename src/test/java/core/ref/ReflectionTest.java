package core.ref;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
				e.printStackTrace();
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
