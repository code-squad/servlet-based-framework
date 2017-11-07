package next.dao;

import java.sql.Timestamp;
import java.util.List;

import next.model.Answer;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.RowMapper;

public class AnswerDao {
	public Answer insert(Answer answer) {
		JdbcTemplate template = new JdbcTemplate();
		KeyHolder keyHolder = new KeyHolder();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		template.update(sql, keyHolder, answer.getWriter(), answer.getContents(),
				new Timestamp(answer.getTimeFromCreateDate()), answer.getQuestionId());
		return findById(keyHolder.getId());
	}

	public Answer findById(long answerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

		RowMapper<Answer> rm = (rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"),
				rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getLong("questionId")));
		return jdbcTemplate.queryForObject(sql, rm, answerId);
	}

	public List<Answer> findAllByQuestionId(long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
				+ "order by answerId desc";

		RowMapper<Answer> rm = (rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"),
				rs.getString("contents"), rs.getTimestamp("createdDate"), questionId));
		return jdbcTemplate.query(sql, rm, questionId);
	}

	public void delete(Answer answer) {
		JdbcTemplate template = new JdbcTemplate();
		KeyHolder keyHolder = new KeyHolder();
		String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
		template.update(sql, keyHolder, answer.getAnswerId());

	}

	public void delete(Long answerId) {
		JdbcTemplate template = new JdbcTemplate();
		KeyHolder keyHolder = new KeyHolder();
		String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
		template.update(sql, keyHolder, answerId);
	}
}
