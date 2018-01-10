package next.web;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import next.dao.AnswerDao;
import next.model.Answer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ApiAcceptanceTest {
	@Autowired
	private Config config;
	private static final Logger log = LoggerFactory.getLogger(ApiAcceptanceTest.class);

	private RestTemplate restTemplate;
	private AnswerDao answerDao;
	private String homeUrl = "http://localhost:";
	private String port = "8080";
	private String url = homeUrl + port;

	@Before
	public void set() throws Exception {
		restTemplate = new RestTemplate();
		answerDao = new AnswerDao();
	}

	@Test
	public void create() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String writer = "abcshc";
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("writer", writer);
		params.add("contents", "123");
		params.add("questionId", "2");
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params,
				headers);

		Answer answer = restTemplate.postForObject(url + "/api/qna/addAnswer", request, Answer.class);

		Answer findAnswer = answerDao.findById(answer.getAnswerId());

		log.debug("body : {}", answer);
		assertNotNull(findAnswer);
	}

	@Configuration
	static class Config {
	}
}
