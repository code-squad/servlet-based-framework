package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;

import java.util.List;

@Service
public class AnswerService {
    private AnswerDao answerDao;

    @Inject
    public AnswerService(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    public Answer insert(Answer answer){
        return answerDao.insert(answer);
    }

    public Result delete(Long answerId){
        return answerDao.delete(answerId);
    }

    public List<Answer> findAll(){
        return answerDao.findAll();
    }

    public List<Answer> findByQuestionId(Long questionId){
        return answerDao.findByQuestionId(questionId);
    }
}
