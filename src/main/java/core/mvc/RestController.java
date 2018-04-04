package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RestController extends Controller {
    @Override
    void executeAjax(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
