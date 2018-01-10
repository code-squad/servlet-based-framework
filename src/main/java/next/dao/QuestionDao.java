package next.dao;

import next.model.Question;

public class QuestionDao {
	public Question findById(long questionId) {
		String sql = "SELECT questionid, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		return jdbcTemplate.queryForObject(sql, rs -> {
			return new Question(rs.getLong("questionid"), rs.getString("writer"), rs.getString("title"),
					rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));
		}, String.valueOf(questionId));
	}
}
