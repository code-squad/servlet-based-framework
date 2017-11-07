package core.nmvc.factory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RepositoryFactoryTest {
	
	private RepositoryFactory rf;
	int interfacesCount;
	
	@Before
	public void setUp() {
		rf = new RepositoryFactory();
		interfacesCount = rf.initialize();
	}

	@Test
	public void is_this_parsing_annotated_interfaces_correctly() {
		assertEquals(1, interfacesCount);
	}
	
	@Test
	public void parsing_unimplemented_methods() {
		assertEquals(1,rf.implementMethods());
	}

}
