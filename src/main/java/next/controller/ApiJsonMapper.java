package next.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.JsonView;
import core.mvc.View;

public class ApiJsonMapper {

	public static View mapping(HttpServletResponse response, Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(mapper.writeValueAsString(object));
		return new JsonView();
	}
}