package core.nmvc;

import org.junit.Test;
import org.reflections.Reflections;

public class ClassPathBeanScannerTest {

    @Test
    public void annotatedBeanTest() throws Exception {
        ClassPathBeanScanner classPathBeanScanner = new ClassPathBeanScanner("core.di.example");
//        Object object = classPathBeanScanner.detectBean();
        Reflections reflections = new Reflections("core.di.example");
    }
}