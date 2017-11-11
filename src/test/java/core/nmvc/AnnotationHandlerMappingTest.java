package core.nmvc;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;

	@Before
	public void setup() {
		handlerMapping = new AnnotationHandlerMapping();
		handlerMapping.initialize();
	}

	@Test
	public void getHandler() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/users/findUserId");
		MockHttpServletResponse resp = new MockHttpServletResponse();
		Optional<Object> execution = handlerMapping.getHandler(req);
		((HandlerExecution) execution.get()).handle(req, resp);
	}
}
