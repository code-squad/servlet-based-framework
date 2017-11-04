package next.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;
import next.model.User;

public class QuestionDaoTest {
	@Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void crud() throws Exception {
    		Question expected = new Question("xmfpes", "hello", "hello, my name is kyunam");
        QuestionDao questionDao = QuestionDao.getInstance();
        expected.setQuestionId(questionDao.insert(expected));
        Question actual = questionDao.findByQuestionId(expected.getQuestionId());
        assertEquals(expected, actual);
    }
}
