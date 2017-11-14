package core.di.factory.example;

import java.util.List;

import core.annotation.Repository;
import next.model.Question;

@Repository
public class JdbcQuestionRepository implements QuestionRepository {

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
