package next.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;

public class QuestionDaoTest {
	@Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
	
	@Test
	public void insert() {
		Question expected = new Question("name1", "title1", "contents1");
		QuestionDao questionDao = QuestionDao.getInstance();
		questionDao.insert(expected);
		Question actual = questionDao.findByQuestionId(expected.getQuestionId());
		assertEquals(expected, actual);
	}

}
