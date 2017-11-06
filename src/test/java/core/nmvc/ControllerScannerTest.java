package core.nmvc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ControllerScannerTest {

	private ControllerScanner cs;

	@Before
	public void setUp() {
		cs = new ControllerScanner("core.nmvc");
	}

	@Test
	public void test() {
		assertTrue(cs.getControllersInstance(MyController.class) instanceof MyController);
	}

}
