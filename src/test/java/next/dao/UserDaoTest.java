package next.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.di.factory.BeanFactory;
import core.nmvc.BeanDefinition;
import core.nmvc.ClassPathBeanScanner;
import core.nmvc.ConfigurationBeanScanner;
import next.exception.NoConfigurationFileException;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import next.model.User;

import javax.sql.DataSource;

public class UserDaoTest {
    private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
    private BeanFactory beanFactory;
    private ClassPathBeanScanner cpbs;
    private ConfigurationBeanScanner cbs;
    private BeanDefinition bf;
    private UserDao userDao;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        // 리플렉션을 통해 annotation 이 붙은 클래스들을 모은다.
        // 클래스들을 빈으로 등록
        Class<?> configuration = detectConfigurationFile("core");
        log.debug("configuration : {}", configuration) ;
        cpbs = new ClassPathBeanScanner(configuration.getAnnotation(ComponentScan.class).basePackages());
        cbs = new ConfigurationBeanScanner(configuration);
        bf = new BeanDefinition(cbs, cpbs);
        beanFactory = new BeanFactory(bf);
        beanFactory.initialize();

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, beanFactory.getBean(DataSource.class));

        userDao = new UserDao(beanFactory.getBean(JdbcTemplate.class));
    }

    private Class<?> detectConfigurationFile(Object configurationFilePackage) {
        return new Reflections(configurationFilePackage).getTypesAnnotatedWith(Configuration.class).
                stream().findFirst().orElseThrow(() -> new NoConfigurationFileException("There is no configuration file"));
    }

    @Test
    public void crud() throws Exception {
        // create
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

        User expected2 = new User("userId2", "dd", "dd", "javajigi@email.com");
        userDao.insert(expected2);
        User actual2 = userDao.findByUserId(expected2.getUserId());
        assertEquals(expected2, actual2);

        // update
        expected.update(new User("userId", "password2", "name2", "sanjigi@email.com"));
        userDao.update(expected);
        actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

        // delete
        userDao.delete("userId2");
        List<User> users = userDao.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
    }
}