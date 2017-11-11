package core.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import next.dao.UserDao;
import next.model.User;

public class JdbcManagerTest {
	
	private JdbcManager jdbm;
	private UserDao ud;
	
	
	@Before
	public void setUp() {
		this.jdbm = JdbcManager.getInstance();
		this.ud = new UserDao();
	}
	
	@Test
	public void GENERATE_QUERYSTRING_BY_OBJECT() {
		User user = new User("pobi", "1234", "김규남", "pobi@naver.com");
		this.jdbm.insertObject("USERS", user);
		assertEquals(ud.findByUserId("pobi"), user);
	}	
}
