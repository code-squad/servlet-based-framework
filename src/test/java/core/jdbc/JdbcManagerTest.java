package core.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import next.model.User;

public class JdbcManagerTest {
	
	private User user;
	
	@Before
	public void setup() {
		this.user = new User("hi", "asdf", "asdf", "asdfasdfas");
	}

	@Test
	public void test() {
		
		
	}

}
