package core.nmvc;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerTest {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);
	ControllerScanner sc = new ControllerScanner();
	@Test
	public void 컨트롤러_찾기_테스트() {
		Map<Class<?>, Object> mapper = sc.getController();
		mapper.keySet().stream().forEach(t -> logger.debug("Controller : "+t.getClass().getName()) );
	}
	
	@Test
	public void 찾은_컨트롤러_확인() throws Exception {
		Map<Class<?>, Object> mapper = sc.getController();
		mapper.keySet().stream().forEach(t -> logger.debug("Controller Name : "+t.getName()));
		
	}

}
