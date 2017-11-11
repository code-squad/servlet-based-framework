package core.mvc;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JspView implements View {
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private static final Logger log = LoggerFactory.getLogger(JspView.class);
	private String viewName;

	public JspView(String viewName) {
		if (viewName == null) {
			throw new NullPointerException("viewName is null. 이동할 URL을 입력하세요.");
		}
		this.viewName = viewName;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		boolean sendType = viewName.startsWith(DEFAULT_REDIRECT_PREFIX);
		SendStrategy sendStrategy = sendType ? new Redirect() : new Forward();
		if(!sendType) {
			setAttributeInKeys(req, model);
		}
		sendStrategy.excuteSend(req, resp, viewName);
	}
	
	private void setAttributeInKeys(HttpServletRequest req, Map<String, ?> model) {
		Set<String> keys = model.keySet();
		for (String key : keys) {
			req.setAttribute(key, model.get(key));
		}
	}

	private interface SendStrategy {
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException;
	}

	private class Redirect implements SendStrategy {
		@Override
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException {
			int idx = url.indexOf(":");
			String redirectUrl = url.substring(idx + 1);
			resp.sendRedirect(redirectUrl);
		}
	}

	private class Forward implements SendStrategy {
		@Override
		public void excuteSend(HttpServletRequest req, HttpServletResponse resp, String url)
				throws ServletException, IOException {
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);
		}
	}
}
