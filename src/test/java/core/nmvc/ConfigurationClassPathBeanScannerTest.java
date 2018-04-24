package core.nmvc;

import core.di.factory.BeanFactory;
import core.di.factory.example.ExampleConfig;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class ConfigurationClassPathBeanScannerTest {

    @Test
    public void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        ConfigurationBeanScanner cbs = new ConfigurationBeanScanner(beanFactory);
        cbs.registser(ExampleConfig.class);
        beanFactory.initialize();

        assertNotNull(beanFactory.getBean(DataSource.class));
    }
}