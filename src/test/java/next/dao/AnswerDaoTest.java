package next.dao;

import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.di.factory.BeanFactory;
import core.nmvc.IntegrationConfig;
import core.nmvc.BeanDefinition;
import core.nmvc.ClassPathBeanScanner;
import core.nmvc.ConfigurationBeanScanner;
import next.exception.NoConfigurationFileException;
import next.model.Answer;
import next.model.Result;
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

public class AnswerDaoTest {
    private BeanFactory beanFactory;
    private ClassPathBeanScanner cpbs;
    private ConfigurationBeanScanner cbs;
    private BeanDefinition bf;
    private AnswerDao answerDao;

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

        answerDao = new AnswerDao(beanFactory.getBean(JdbcTemplate.class));
    }

    private Class<?> detectConfigurationFile(Object configurationFilePackage) {
        return new Reflections(configurationFilePackage).getTypesAnnotatedWith(Configuration.class).
                stream().findFirst().orElseThrow(() -> new NoConfigurationFileException("There is no configuration file"));
    }

    @Test
    public void insert() throws Exception {
        Answer answer = new Answer("jiwon", "contents", 1L);
        Answer created = answerDao.insert(answer);
        assertEquals("jiwon", created.getWriter());
        assertEquals(6, created.getAnswerId());
    }

    @Test
    public void findById() throws Exception {
        Answer answer = answerDao.findById(1L);
        String firstWriter = "eungju";
        assertEquals(firstWriter, answer.getWriter());
        assertEquals(1L, answer.getAnswerId());
    }

    @Test
    public void findAll() throws Exception {
        List<Answer> answers = answerDao.findAll();
        assertEquals(5, answers.size());
    }

    @Test
    public void findByQuestionId() throws Exception {
        List<Answer> answers = answerDao.findByQuestionId(7L);
        assertEquals(2, answers.size());
    }

    @Test
    public void deleteAnswer() throws Exception {
        Result rs = answerDao.delete(5L);
        answerDao.delete(4L);

        assertEquals(3, answerDao.findAll().size());
        assertEquals(true, rs.isStatus());
    }
}