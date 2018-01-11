package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
	private String urlMessage;

	public JspView(String urlMessage) {
		this.urlMessage = urlMessage;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (urlMessage.startsWith("redirect:")) {
			String value[] = urlMessage.split(":");
			response.sendRedirect(value[1]);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher(urlMessage);
			rd.forward(request, response);
		}
	}
}