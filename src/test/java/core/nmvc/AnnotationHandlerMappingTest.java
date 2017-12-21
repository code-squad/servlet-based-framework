package core.nmvc;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
		assertTrue(true);
	}

	@Test
	public void gandlerUsersTest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Optional<?> execution = handlerMapping.getHandler(request);
		((HandlerExecution) execution.get()).handle(request, response);
		assertTrue(true);
	}
}
