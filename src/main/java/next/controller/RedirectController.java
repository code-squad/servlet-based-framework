package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

public class RedirectController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
	String url;
	public RedirectController(){
		this.url = "/";
	}
	public RedirectController(String url){
		this.url = url;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
        		request.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:"+url;
	}
	
}
