package next.service;

import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;

import java.util.List;

public class AnswerService {
    private AnswerDao answerDao;

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
