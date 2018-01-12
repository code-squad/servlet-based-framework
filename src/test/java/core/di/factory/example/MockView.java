package core.di.factory.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.View;

public class MockView implements View {

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    }

}
