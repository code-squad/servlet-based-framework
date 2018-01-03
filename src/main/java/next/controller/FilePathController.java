package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilePathController implements Controller {
	private String url;
	
	FilePathController(String url){
		this.url = url;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return url;
	}

}
