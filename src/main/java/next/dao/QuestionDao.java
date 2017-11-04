package next.dao;

import java.util.List;

import next.model.Question;

public class QuestionDao {
	private static final QuestionDao questionDao = new QuestionDao();
	public static QuestionDao getInstance() {
		// TODO Auto-generated method stub
		return questionDao;
	}

	public int insert(Question question) {
		// TODO Auto-generated method stub
		JdbcTemplate insertTemplate = JdbcTemplate.getInstance();
		return insertTemplate.update("INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)", question.getWriter(), 
				question.getTitle(), question.getContents(), question.getCreatedDate(), question.getCountOfComment()); 
	}

	public Question findByQuestionId(long questionId) {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.queryForObject("SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS " + "WHERE questionId=?"
                + "order by questionId desc" , (rs) -> {
	                	return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
	                            rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
                }, questionId);
	}

	public List<Question> findAll() {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.query("SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc" , (rs) -> {
	                	return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
	                            rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
                });
	}
}
