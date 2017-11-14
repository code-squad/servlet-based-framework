package core.di.factory.example;

import java.util.List;

import next.model.Question;

public interface QuestionRepository {
	public List<Question> findAll();
}
