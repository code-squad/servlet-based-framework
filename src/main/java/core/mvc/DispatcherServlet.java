package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// url 과 서블릿 매핑을 urlPatterns 속성을 통해 해주고 있음.
// 모든 클라이언트 요청을 받는 서블릿
// 서블릿 컨테이너 역할
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping requestMapping;

    @Override
    public void init() {
        requestMapping = new RequestMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        log.debug("url : {}", url);

        Controller controller = requestMapping.find(url);

        if (isAjaxReq(url)){
            controller.executeAjax(req, resp);
            return;
        }

        String location = controller.execute(req, resp);
        log.debug("location :  {}", location);

        // redirect
        if (executeRedirect(resp, location)) return;
        // forward
        forward(req, resp, location);
        return;
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String location) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(location);
        rd.forward(req, resp);
    }

    private boolean executeRedirect(HttpServletResponse resp, String location) throws IOException {
        if (location.startsWith("redirect:")) {
            resp.sendRedirect(location.substring(9));
            return true;
        }
        return false;
    }

    private boolean isAjaxReq(String url) throws IOException {
        if(url.contains("api")) {
            return true;
        }
        return false;
    }

}
