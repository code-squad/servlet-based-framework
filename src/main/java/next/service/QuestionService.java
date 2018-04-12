package next.service;

import next.dao.QuestionDao;
import next.model.Question;

public class QuestionService {
    private QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void insert(Question question){
        questionDao.insert(question);
    }

    public Question findById(Long questionId){
        return questionDao.findById(questionId);
    }
}
