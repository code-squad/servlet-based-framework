package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {

    private String url;

    public JspView(String url) {
        this.url = url;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
