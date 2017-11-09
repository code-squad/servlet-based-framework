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
		logger.debug(clazz.getName());
		Field[] fields = clazz.getDeclaredFields();
		Constructor<?>[] constructors = clazz.getConstructors();
		Method[] methods = clazz.getMethods();
		for(Field field : fields) {
			logger.debug("field : " + field);
		}
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
        logger.debug(clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        Student st = new Student();
        Arrays.stream(fields).forEach(f -> {
        		f.setAccessible(true);
        		try {
					if(f.getType().equals(String.class)) {
						f.set(st, "재성");
					}else {
						f.set(st, 15);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
        });
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
