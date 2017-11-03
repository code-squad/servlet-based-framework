package core.ref;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        
        Arrays.asList(clazz.getDeclaredMethods()).stream()
        .filter(m -> m.isAnnotationPresent(MyTest.class)).forEach(m -> {
			try {
				m.invoke(clazz.newInstance());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException e) {
				e.printStackTrace();
			}
		});
        

    }
}
