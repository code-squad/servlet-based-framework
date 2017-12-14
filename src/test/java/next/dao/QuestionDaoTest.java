package next.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;

public class QuestionDaoTest {
	QuestionDao questionDao;

	@Before
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
		questionDao = QuestionDao.getInstance();
	}

	@Test
	public void crud() {
		//insert
		Question expected = new Question(9, "name1", "title1", "contents1", new Date(), 0);
		questionDao.insert(expected);
		//read
		Question actual = questionDao.findByQuestionId(9);
		assertEquals(expected, actual);

		//update
		expected.update("수정자", "new Title", "new Contents");
		questionDao.update(expected);
		actual = questionDao.findByQuestionId(expected.getQuestionId());
		assertEquals(expected, actual);
		assertEquals(actual.getTitle(), "new Title");
		
		//delete
		questionDao.delete(9);
		actual = questionDao.findByQuestionId(9);
		assertNull(actual);
	}

	@Test
	public void findAllTest() {
		List<Question> questions = questionDao.findAll();
		assertEquals(8, questions.size());
	}
}
