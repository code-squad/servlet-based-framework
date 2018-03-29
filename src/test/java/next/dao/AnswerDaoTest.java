package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import static org.junit.Assert.*;

public class AnswerDaoTest {

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }


    @Test
    public void insert() throws Exception {
        Answer answer = new Answer("jiwon", "contents", 1L);
        AnswerDao answerDao = new AnswerDao();
        Answer created = answerDao.insert(answer);
        assertEquals("jiwon", created.getWriter());
    }

    @Test
    public void findById() throws Exception {
        AnswerDao answerDao = new AnswerDao();
        Answer answer = answerDao.findById(1L);
        String firstWriter = "eungju";
        assertEquals(firstWriter, answer.getWriter());
    }
}