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
	
	public void update(Question question) {
		String sql = "UPDATE QUESTIONS set writer = ?, title = ?, contents = ? WHERE questionId = ?";
		jdbcTemplate.update(sql, (rs) -> {
			rs.setString(1, question.getWriter());
			rs.setString(2, question.getTitle());
			rs.setString(3, question.getContents());
			rs.setLong(4, question.getQuestionId());
		});
	}
	
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
