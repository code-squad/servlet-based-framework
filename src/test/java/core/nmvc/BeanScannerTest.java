package core.nmvc;

import org.junit.Test;
import org.reflections.Reflections;

import static org.junit.Assert.*;

public class BeanScannerTest {

    @Test
    public void annotatedBeanTest() throws Exception {
        BeanScanner beanScanner = new BeanScanner("core.di.example");
//        Object object = beanScanner.detectBean();
        Reflections reflections = new Reflections("core.di.example");
    }
}