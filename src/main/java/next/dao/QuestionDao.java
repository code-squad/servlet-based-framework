package next.dao;

import java.util.List;

import next.model.Question;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class QuestionDao {

	private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
	private static QuestionDao questionDao = new QuestionDao();


	public static QuestionDao getInstance() {
		return questionDao;
	}

	public List<Question> findAll() {
		String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
				+ "order by questionId desc";

		RowMapper<Question> rm = (rs -> new Question(rs.getLong("questionId"), rs.getString("writer"),
				rs.getString("title"), null, rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer")));
		return JdbcTemplate.getInstance().query(sql, rm);
	}

	public Question findById(long questionId) {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
				+ "WHERE questionId = ?";

		RowMapper<Question> rm = (rs -> new Question(rs.getLong("questionId"), rs.getString("writer"),
				rs.getString("title"), rs.getString("contents"), rs.getTimestamp("createdDate"),
				rs.getInt("countOfAnswer")));
		return JdbcTemplate.getInstance().queryForObject(sql, rm, questionId);
	}
}
