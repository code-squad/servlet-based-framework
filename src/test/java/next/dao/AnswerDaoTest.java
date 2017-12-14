package next.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Answer;

public class AnswerDaoTest {
	AnswerDao answerDao;

	@Before
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
		answerDao = AnswerDao.getInstance();
	}

	@Test
	public void insert() {
		Answer answer = new Answer("댓글러", "첫댓글", 2);
		Answer actual =answerDao.insert(answer);
		assertEquals(actual.getContents(), answer.getContents());
	}

}
