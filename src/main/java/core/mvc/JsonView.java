package core.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {
	private Object object;

	public JsonView(Object object) {
		this.object = object;
	}

	public void mapping(HttpServletResponse response, Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(mapper.writeValueAsString(object));
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		mapping(response, object);
	}
}
