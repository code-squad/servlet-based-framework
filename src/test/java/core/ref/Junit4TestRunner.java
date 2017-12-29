package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
	@Test
	public void run() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(MyTest.class)) {
				method.invoke(clazz.newInstance());
			}
		}
	}
}
