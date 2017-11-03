package core.ref;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        
        
        Arrays.asList(methods).stream()
        		.filter(m -> m.getName().startsWith("test")).forEach(m -> {
					try {
						m.invoke(clazz.newInstance());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| InstantiationException e) {
						e.printStackTrace();
					}
				});
        
       
    }
}
