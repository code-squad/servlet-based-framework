package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

public class ForwardController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
	String url;
	public ForwardController(){
		this.url = "/";
	}
	public ForwardController(String url){
		this.url = url;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return url+".jsp";
	}
	


}
