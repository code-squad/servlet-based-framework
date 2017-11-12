package core.ref;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit3TestRunner {
	private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

	@Test
	public void run() throws Exception {
		Class<Junit3Test> clazz = Junit3Test.class;
		Method[] ms = clazz.getDeclaredMethods();

		Arrays.stream(ms).forEach(m -> logger.debug(m.toString()));
		Arrays.stream(ms).forEach(m -> logger.debug(m.toString()));

		Arrays.stream(ms).filter(i -> i.toString().startsWith("public void core.ref.Junit3Test.test")).forEach(m -> {
			try {
				m.invoke(clazz.newInstance());

			} catch (Exception e) {

			}
		});

		// m.invoke(clazz.newInstance());
	}
}
