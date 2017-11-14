package core.nmvc.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.User;

public class PrimaryKeyTest {
	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyTest.class);
	
	private User user;
	
	@Before
	public void setUp() {
		this.user = new User("javajigi", "password", "박재성", "slipp@javajigi.net");
	}

	@Test
	public void test() {
		PrimaryKey key = new PrimaryKey(this.user);
		log.debug(key.getKeyObject().toString());
		assertNotNull(key.getKeyObject());
	}

}
