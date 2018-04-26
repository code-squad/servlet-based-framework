package next.dao;

import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.di.factory.BeanFactory;
import core.jdbc.ConnectionManager;
import core.nmvc.BeanDefinition;
import core.nmvc.ClassPathBeanScanner;
import core.nmvc.ConfigurationBeanScanner;
import core.nmvc.IntegrationConfig;
import next.exception.NoConfigurationFileException;
import next.model.Answer;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionDaoTest {
    private BeanFactory beanFactory;
    private ClassPathBeanScanner cpbs;
    private ConfigurationBeanScanner cbs;
    private BeanDefinition bf;
    private QuestionDao questionDao;
    private static final Logger log = LoggerFactory.getLogger(AnswerDao.class);

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

        questionDao = new QuestionDao(beanFactory.getBean(JdbcTemplate.class));
    }

    private Class<?> detectConfigurationFile(Object configurationFilePackage) {
        return new Reflections(configurationFilePackage).getTypesAnnotatedWith(Configuration.class).
                stream().findFirst().orElseThrow(() -> new NoConfigurationFileException("There is no configuration file"));
    }
    @Test
    public void insert() throws Exception {
        Question question = new Question("writer", "title", "contents");
        Question created = questionDao.insert(question);
        assertEquals("writer", created.getWriter());
        assertEquals(9, created.getQuestionId());
    }

    @Test
    public void findById() throws Exception {
        Question question = questionDao.findById(1L);
        String firstWriter = "자바지기";
        assertEquals(firstWriter, question.getWriter());
    }

    @Test
    public void findAll() throws Exception {
        List<Question> list = questionDao.findAll();
        assertEquals(8, list.size());
    }
}