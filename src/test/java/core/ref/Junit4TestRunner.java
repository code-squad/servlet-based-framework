package core.ref;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit4TestRunner {

	@Test
	public void run() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;
		Method[] ms = clazz.getDeclaredMethods();
		Arrays.stream(ms).filter(m -> m.isAnnotationPresent(MyTest.class)).forEach(m -> {
			try {
				m.invoke(clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
}
