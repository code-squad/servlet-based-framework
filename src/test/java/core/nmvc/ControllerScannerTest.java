package core.nmvc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.di.factory.BeanScanner;

public class ControllerScannerTest {

	private BeanScanner cs;

	@Before
	public void setUp() {
		cs = new BeanScanner("core.nmvc");
	}

	@Test
	public void test() {
		assertTrue(cs.getControllersInstance(MyController.class) instanceof MyController);
	}

}
