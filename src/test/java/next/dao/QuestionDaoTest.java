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
	public void insertTest() {
		Question expected = new Question(9, "name1", "title1", "contents1", new Date(), 0);
		questionDao.insert(expected);
		Question actual = questionDao.findByQuestionId(9);
		assertEquals(expected, actual);
	}

	@Test
	public void findAllTest() {
		List<Question> questions = questionDao.findAll();
		assertEquals(8, questions.size());
	}
	
	@Test
	public void deleteTest() {
		List<Question> questions = questionDao.findAll();
		questionDao.delete(4);
		questions = questionDao.findAll();
		assertEquals(7, questions.size());
	}
	
	@Test
	public void updateTest() {
		Question question = questionDao.findByQuestionId(3);
		question.update("수정자","new Title", "new Contents");
		questionDao.update(question);

		Question modifyQuestion = questionDao.findByQuestionId(3);
		assertEquals(question, modifyQuestion);
		assertEquals(modifyQuestion.getTitle(),"new Title");
	}

}
