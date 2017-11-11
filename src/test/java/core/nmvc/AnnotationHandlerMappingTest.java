package core.nmvc;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;


	@Test
	public void getHandler() throws Exception {
		handlerMapping = new AnnotationHandlerMapping("core.nmvc");
		handlerMapping.initialize();
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
		MockHttpServletResponse response = new MockHttpServletResponse();
		HandlerExecution execution = handlerMapping.getHandler(request);
		execution.handle(request, response);
	}
}
