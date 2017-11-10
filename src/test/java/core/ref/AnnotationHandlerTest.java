package core.ref;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.ControllerScanner;

public class AnnotationHandlerTest {
	private static ControllerScanner controllerScanner;
	private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerTest.class);

	@Test
	public void scanControllerTest() {
		Object [] obj = {"core.ref"};
		controllerScanner = new ControllerScanner(obj);
		assertTrue(controllerScanner.getControllerInstance(Student.class) instanceof Student);
	}
}
