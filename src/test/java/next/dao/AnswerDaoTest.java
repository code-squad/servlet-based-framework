package next.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Answer;

public class AnswerDaoTest {
	private AnswerDao answerDao;

	@Before
	public void setUp() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
		answerDao = AnswerDao.getInstance();
	}

	@Test
	public void insert() {
		Answer answer = new Answer("댓글러", "첫댓글", 2);
		Answer actual = answerDao.insert(answer);
		assertEquals(actual.getContents(), answer.getContents());
	}

	@Test
	public void findAllByQuestionId() {
		// 7번 Question에 2개의 Answer가 추가되어 있다.
		List<Answer> answers = answerDao.findAllByQuestionId(7);
		assertEquals(2, answers.size());
	}

	@Test
	public void deleteTest() {
		List<Answer> answers = answerDao.findAllByQuestionId(7);
		Answer answer = answers.get(0);
		int originalSize = answers.size();

		answerDao.delete(answer.getAnswerId());
		answers = answerDao.findAllByQuestionId(7);

		assertEquals(originalSize - 1, answers.size());
	}
}
