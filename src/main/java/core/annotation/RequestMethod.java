package core.annotation;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
    
    public static RequestMethod getMethodEnum(HttpServletRequest req) {
    		return Arrays.asList(RequestMethod.values()).stream()
    				.filter(e -> e.toString().equalsIgnoreCase(req.getMethod()))
    				.findFirst().get();
    				
    				
    }
}
