package core.nmvc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ControllerScannerTest {
	
	private ControllerScanner cs;
	
	@Before
	public void setup() {
		cs = new ControllerScanner("core.nmvc");
	}

	@Test
	public void test() {
		cs.findControllers();
		assertTrue(cs.getControllers(MyController.class) instanceof MyController);
	}

}
