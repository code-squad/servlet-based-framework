package next.dao;

import java.util.List;

import next.model.Question;

public class QuestionDao {
	private static final QuestionDao questionDao = new QuestionDao();
	private static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
	public static QuestionDao getInstance() {
		// TODO Auto-generated method stub
		return questionDao;
	}

	public Question insert(Question question) {
		return findByQuestionId(jdbcTemplate.update(
				"INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)",
				question.getWriter(), question.getTitle(), question.getContents(), question.getCreatedDate(),
				question.getCountOfComment()));
	}

	public Question findByQuestionId(long questionId) {
		return jdbcTemplate.queryForObject("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
						+ "WHERE questionId=?" + "order by questionId desc", (rs) -> {
							return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
									rs.getString("contents"), rs.getTimestamp("createdDate"),
									rs.getInt("countOfAnswer"));
						}, questionId);
	}

	public List<Question> findAll() {
		return jdbcTemplate.query("SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
						+ "order by questionId desc", (rs) -> {
							return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
									rs.getString("contents"), rs.getTimestamp("createdDate"),
									rs.getInt("countOfAnswer"));
						});
	}

	public int getCountOfAnswer(long questionId) {
		return jdbcTemplate.queryForObject("SELECT countOfAnswer FROM QUESTIONS WHERE questionId=?", (rs) -> {
			return rs.getInt("countOfAnswer");
		}, questionId);
	}

	public void editCountOfAnswer(long questionId, int count) {
		int countOfAnswer = getCountOfAnswer(questionId);
		jdbcTemplate.update("UPDATE QUESTIONS SET countOfAnswer=? WHERE questionId = ?", (pstmt) -> {
			pstmt.setInt(1, (countOfAnswer + count));
			pstmt.setLong(2, questionId);
		});
	}

}
