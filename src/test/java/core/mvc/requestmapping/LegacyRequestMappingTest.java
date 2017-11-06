package core.mvc.requestmapping;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.controller.LegacyControllerInterface;

public class LegacyRequestMappingTest {
	LegacyRequestMapping lrm;
	MockTestController mtc = new MockTestController();

	@Before
	public void setup() {
		this.lrm = LegacyRequestMapping.getInstance();
		this.lrm.addController(mtc, new RequestLine("/test", RequestMethod.GET));
	}

	@Test

	public void test() {
		assertEquals(mtc, this.lrm.getController(new MockHttpServletRequest("GET", "/test")));
	}

	class MockTestController implements LegacyControllerInterface {

		@Override
		public ModelAndView run(HttpServletRequest req) {
			return null;
		}

	}

}
