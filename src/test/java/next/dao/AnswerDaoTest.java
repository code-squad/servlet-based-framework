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
	@Before
    public void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer answer = new Answer("javajigi", "answer contents", questionId);
        AnswerDao answerDao = AnswerDao.getInstance();
        Answer insertedAnswer = answerDao.findByAnswerId(1);
        assertEquals(answer, insertedAnswer);
    }
    
    @Test
    public void findAll() throws Exception {
    		AnswerDao answerDao = AnswerDao.getInstance();
        List<Answer> answers = answerDao.findAllByQuestionId(8);
        assertEquals(3, answers.size());
    }
    
    @Test
    public void removeAnswer() throws Exception {
    		long questionId = 1L;
        Answer answer = new Answer("javajigi", "answer contents", questionId);
        AnswerDao answerDao = AnswerDao.getInstance();
        
        Answer insertedAnswer = answerDao.insert(answer);
        long answerId = insertedAnswer.getAnswerId();
        answerDao.delete(answerId);
        assertNull(answerDao.findByAnswerId(answerId));
        
    }
}
