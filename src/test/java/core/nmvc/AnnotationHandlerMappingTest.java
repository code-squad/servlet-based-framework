package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @Before
    public void setup() {
        handlerMapping = new AnnotationHandlerMapping("core.nmvc");
        handlerMapping.initialize();
    }


    @Test
    public void getHandler() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
        HandlerExecution execution = handlerMapping.getController(request);
        execution.handle(request);
    }
}
