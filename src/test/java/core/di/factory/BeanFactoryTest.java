package core.di.factory;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.util.Set;

import core.di.factory.example.*;
import core.nmvc.BeanDefinition;
import core.nmvc.ClassPathBeanScanner;
import core.nmvc.ConfigurationBeanScanner;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

import javax.sql.DataSource;

public class BeanFactoryTest {
    private BeanFactory beanFactory;
    private ClassPathBeanScanner cpbs;
    private ConfigurationBeanScanner cbs;
    private BeanDefinition bf;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        // 리플렉션을 통해 annotation 이 붙은 클래스들을 모은다.
        // 클래스들을 빈으로 등록
        cpbs = new ClassPathBeanScanner("core.di.factory.example");
        cbs = new ConfigurationBeanScanner(IntegrationConfig.class);
        bf = new BeanDefinition(cbs, cpbs);
        beanFactory = new BeanFactory(bf);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    public void di_configuration() {
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    public void register_classpathBeanScanner_통합() {

        assertNotNull(beanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
        assertNotNull(userRepository);
        assertNotNull(userRepository.getJdbcTemplate());
        assertNotNull(userRepository.getJdbcTemplate().getDataSource());

        MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(jdbcTemplate);
        assertNotNull(jdbcTemplate.getDataSource());
    }
}
