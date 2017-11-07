package next.dao;

import java.util.List;

import next.model.Question;

public class QuestionDao {
	private static final QuestionDao questionDao = new QuestionDao();
	public static QuestionDao getInstance() {
		// TODO Auto-generated method stub
		return questionDao;
	}

	public Question insert(Question question) {
		// TODO Auto-generated method stub
		JdbcTemplate insertTemplate = JdbcTemplate.getInstance();
		return findByQuestionId(insertTemplate.update("INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)", question.getWriter(), 
				question.getTitle(), question.getContents(), question.getCreatedDate(), question.getCountOfComment())); 
	}

	public Question findByQuestionId(long questionId) {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.queryForObject("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS " + "WHERE questionId=?"
                + "order by questionId desc" , (rs) -> {
                		return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                            rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
                }, questionId);
	}

	public List<Question> findAll() {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.query("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc" , (rs) -> {
                		return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                            rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
                });
	}
	public int getCountOfAnswer(long questionId) {
		JdbcTemplate insertTemplate = JdbcTemplate.getInstance();
		return insertTemplate.queryForObject("SELECT countOfAnswer FROM QUESTIONS WHERE questionId=?", (rs) -> {
			return rs.getInt("countOfAnswer");
		}, questionId);
	}
	public void editCountOfAnswer(long questionId, int count) {
		JdbcTemplate insertTemplate = JdbcTemplate.getInstance();
		int countOfAnswer = getCountOfAnswer(questionId);
		insertTemplate.update("UPDATE QUESTIONS SET countOfAnswer=? WHERE questionId = ?" , (pstmt) -> {
        		pstmt.setInt(1,(countOfAnswer + count));
        		pstmt.setLong(2, questionId);
        });
	}
	
}
