package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CommonController extends Controller {
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
