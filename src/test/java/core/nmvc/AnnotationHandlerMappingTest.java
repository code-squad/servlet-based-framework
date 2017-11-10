package core.nmvc;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import next.controller.LegacyController;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;

	@Before
	public void setup() {
		handlerMapping = new AnnotationHandlerMapping("core.nmvc");
		handlerMapping.initialize();
	}

	@Test
	public void getHandler() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/users/findUserId");
		MockHttpServletResponse resp = new MockHttpServletResponse();
		Optional<LegacyController> execution = handlerMapping.getHandler(req);
		execution.get().execute(req, resp);
	}
}
