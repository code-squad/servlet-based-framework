package core.web.requestservlet;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class DispatcherServletTest {
	
	MockHttpServletRequest mhsr;
	
	@Before
	public void setup() {
		mhsr = new MockHttpServletRequest("GET", "/home");
	}

	@Test
	public void test() {
		DispatcherServlet ds = new DispatcherServlet();
		ds.init();
		
		try {
			ds.service(mhsr, new MockHttpServletResponse());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
