package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface Controller {

    default String execute(HttpServletRequest req, HttpServletResponse res) throws IOException{ return null; }
    default void executeAjax(HttpServletRequest req, HttpServletResponse res) throws IOException{ }


}
