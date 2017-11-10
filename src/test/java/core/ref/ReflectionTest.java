package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	public void showClass() {
		Class<Question> clazz = Question.class;
		
		Field[] fields = clazz.getDeclaredFields();
		Constructor<?>[] constructors = clazz.getConstructors();
		Method[] methods = clazz.getMethods();
		
		Arrays.stream(fields).forEach(f -> logger.debug("fields :" + f));
		Arrays.stream(constructors).forEach(s -> logger.debug("constructor : " + s));
		Arrays.stream(methods).forEach(m -> logger.debug("method : " + m));

	
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
	
	@Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        Field[] fields = clazz.getDeclaredFields();
        Student st = new Student();
        fields[0].setAccessible(true);
        fields[1].setAccessible(true);
        try {
			fields[0].set(st, "pobi");
			fields[1].set(st, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println(st.getAge());
        System.out.println(st.getName());
    }
	@Test
	public void createUserTest() {
		Class<User> clazz = User.class;
		
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Arrays.stream(constructors).forEach(c -> {
			try {
				User user = (User) c.newInstance("kyunam", "hello", "hi", "haha");
				System.out.println(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
