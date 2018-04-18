package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.dao.QuestionDao;
import next.model.Question;

import java.util.List;

@Service
public class QuestionService {
    private QuestionDao questionDao;

    @Inject
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void insert(Question question){
        questionDao.insert(question);
    }

    public Question findById(Long questionId){
        return questionDao.findById(questionId);
    }
    public List<Question> findAll() { return questionDao.findAll(); }
}
