package next.dao;

import java.sql.Timestamp;
import java.util.List;

import next.model.Answer;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.RowMapper;

public class AnswerDao {

	private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
	private static AnswerDao answerDao = new AnswerDao();

	public static AnswerDao getInstance() {
		return answerDao;
	}

	public Answer insert(Answer answer) {
		KeyHolder keyHolder = new KeyHolder();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";

		jdbcTemplate.update(sql, keyHolder, answer.getWriter(), answer.getContents(),

				new Timestamp(answer.getTimeFromCreateDate()), answer.getQuestionId());
		return findById(keyHolder.getId());
	}

	public Answer findById(long answerId) {
		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

		RowMapper<Answer> rm = (rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"),
				rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getLong("questionId")));
		return JdbcTemplate.getInstance().queryForObject(sql, rm, answerId);
	}

	public List<Answer> findAllByQuestionId(long questionId) {
		String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
				+ "order by answerId desc";
		RowMapper<Answer> rm = (rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"),
				rs.getString("contents"), rs.getTimestamp("createdDate"), questionId));
		return JdbcTemplate.getInstance().query(sql, rm, questionId);
	}

	public void delete(Answer answer) {
		KeyHolder keyHolder = new KeyHolder();
		String sql = "DELETE FROM ANSWERS WHERE answerId = ?";

		jdbcTemplate.update(sql, keyHolder, answer.getAnswerId());

	}

	public void delete(Long answerId) {
		KeyHolder keyHolder = new KeyHolder();
		String sql = "DELETE FROM ANSWERS WHERE answerId = ?";

		jdbcTemplate.update(sql, keyHolder, answerId);
	}
}
