package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.View;

public class FilePathController implements Controller {
	private String url;
	
	FilePathController(String url){
		this.url = url;
	}

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new JspView(url);
	}

}
