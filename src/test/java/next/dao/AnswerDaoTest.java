package next.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
	}
	
    @Test
    public void crud() throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date date = sdf.parse("2017/1/8");
    	//생성자에 answerId 입력이 되어야 됨
        Answer expected = new Answer(6, "writer", "contents", new Timestamp(date.getTime()), 7);
        AnswerDao answerDao = new AnswerDao();
        long answerId = answerDao.insert(expected);
        //위 생성자 문제로 다시한번더 값을 초기화
        expected = new Answer(answerId, "writer", "contents", new Timestamp(date.getTime()), 7);
        Answer actual = answerDao.findById(expected.getAnswerId());
        assertEquals(expected, actual);
        
        Date date2 = sdf.parse("2018/1/9");
        expected.update(new Answer(answerId, "writer", "contents2", new Timestamp(date2.getTime()), 7));
        answerDao.update(expected);
        actual = answerDao.findById(expected.getAnswerId());
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        AnswerDao answerDao = new AnswerDao();
        List<Answer> answers = answerDao.findAll();
        assertEquals(5, answers.size());
    }

}
