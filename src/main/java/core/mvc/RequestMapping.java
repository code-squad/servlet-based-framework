package core.mvc;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    // match URL with Controller
    Map<String, Controller> controllers;


    public RequestMapping() {
        controllers = new HashMap<>();
        controllers.put("/api/qna/addAnswer", new AddAnswerController());
        controllers.put("/", new HomeController());
        controllers.put("/qna/form", new FowardController("/qna/form"));
        controllers.put("/qna/create", new CreateQuestionController());
        controllers.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        controllers.values().forEach(c -> log.debug("mapped request : {}", c.getClass().getName()));
    }

    public Controller find(String url) {
        return controllers.get(url);
    }


}
