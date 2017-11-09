package core.ref;

import org.junit.Before;
import org.junit.Test;

import core.annotation.scan.ControllerScanner;
import core.nmvc.MyController;

public class ControllerScannerTest {
	private static ControllerScanner controllerScanner;

	@Before
	public void setUp() {
		controllerScanner = new ControllerScanner();
	}
	@Test
	public void scanControllerTest() {
		
		controllerScanner.getController();
	}
}
