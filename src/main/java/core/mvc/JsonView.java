package core.mvc;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import next.model.Result;

public class JsonView implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    		ObjectMapper mapper = new ObjectMapper();
	    	try {
	        response.setContentType("application/json;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.print(mapper.writeValueAsString(model));
	        
	    	} catch(Exception e) {
	    		PrintWriter out = response.getWriter();
	    		out.print(mapper.writeValueAsString(Result.fail(e.getMessage())));
	    	}
    }

}
