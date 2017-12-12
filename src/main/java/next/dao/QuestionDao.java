package next.dao;


import java.sql.Timestamp;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.Question;

public class QuestionDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private final static QuestionDao questionDao = new QuestionDao();
	
	public static QuestionDao getInstance() {
		return questionDao;
	}
	public void insert(Question question) {
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, pstmt -> {
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4, new Timestamp(question.getCreatedDate().getTime()));
			pstmt.setInt(5, question.getCountOfComment());
		});
	}
//	public void insert(Question question) {
//		String sql = "INSERT INTO QUESTIONS (questionId, writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?, ?)";
//		jdbcTemplate.update(sql, pstmt -> {
//			pstmt.setLong(1, question.getQuestionId());
//			pstmt.setString(2, question.getWriter());
//			pstmt.setString(3, question.getTitle());
//			pstmt.setString(4, question.getContents());
//			pstmt.setTimestamp(5, new Timestamp(question.getCreatedDate().getTime()));
//			pstmt.setInt(6, question.getCountOfComment());
//		});
//	}
	
	public Question findByQuestionId(long questionId) {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";
		return jdbcTemplate.queryForObject(sql, rs ->
			new Question(
					rs.getLong("questionId"),
					rs.getString("writer"),
					rs.getString("title"),
					rs.getString("contents"),
					rs.getDate("createdDate"),
					rs.getInt("countOfAnswer")
					)
				, questionId);
	}

	public List<Question> findAll() {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";
		return jdbcTemplate.query(sql, rs -> 
			new Question(
					rs.getLong("questionId"),
					rs.getString("writer"),
					rs.getString("title"),
					rs.getString("contents"),
					rs.getDate("createdDate"),
					rs.getInt("countOfAnswer")
			)
		);
	}
	
	public void delete(long questionId) {
		String sql = "DELETE FROM QUESTIONS WHERE questionId=?";
		jdbcTemplate.update(sql, rs ->
			rs.setLong(1, questionId)
		);
	}
}
