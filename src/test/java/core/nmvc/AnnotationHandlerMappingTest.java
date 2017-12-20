package core.nmvc;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.web.DispatcherServlet;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;

	@Before
	public void setup() {
		handlerMapping = new AnnotationHandlerMapping("core.nmvc");
		handlerMapping.initialize();
	}

	@Test
	public void findUserIdTest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Optional<?> execution = handlerMapping.getHandler(request);
		((HandlerExecution) execution.get()).handle(request, response);
	}

	@Test
	public void gandlerUsersTest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Optional<?> execution = handlerMapping.getHandler(request);
		((HandlerExecution) execution.get()).handle(request, response);

	}
}
