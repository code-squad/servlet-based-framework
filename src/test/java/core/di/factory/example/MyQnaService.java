package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyQnaService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    @Inject
    public MyQnaService(JdbcUserRepository userRepository, JdbcQuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}
