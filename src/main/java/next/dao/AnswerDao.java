package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import next.model.Answer;

public class AnswerDao {
	public Long insert(Answer answer) {
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, answer.getWriter());
				pstmt.setString(2, answer.getContents());
				pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
				pstmt.setLong(4, answer.getQuestionId());
				return pstmt;
			}
		};
		KeyHolder keyHolder = new KeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		return keyHolder.getId();
	}

	public void update(Answer answer) {
		String sql = "UPDATE ANSWERS SET contents=?, createdDate=? WHERE answerid=?";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, answer.getContents(), answer.getCreatedDate().toString(),
				String.valueOf(answer.getAnswerId()));
	}

	public Answer findById(long id) {
		String sql = "SELECT answerid, writer, contents, createddate, questionid FROM ANSWERS WHERE answerid=?";
		Answer answer = null;
		JdbcTemplate template = new JdbcTemplate();
		answer = template.<Answer>queryForObject(sql, (ResultSet rs) -> {
			return new Answer(rs.getLong("answerid"), rs.getString("writer"), rs.getString("contents"),
					rs.getDate("createddate"), rs.getLong("questionid"));
		}, String.valueOf(id));
		return answer;
	}

	public List<Answer> findAll() {
		String sql = "SELECT answerid, writer, contents, createddate, questionid FROM ANSWERS";
		List<Answer> answers = new ArrayList<Answer>();
		JdbcTemplate template = new JdbcTemplate();
		answers = template.<Answer>query(sql, rs -> {
			return new Answer(rs.getLong("answerid"), rs.getString("writer"), rs.getString("contents"),
					rs.getDate("createddate"), rs.getLong("questionid"));
		});
		return answers;
	}

	public List<Answer> findAllByQuestionId(long questionId) {
		String sql = "SELECT answerid, writer, contents, createddate, questionid FROM ANSWERS WHERE questionid=?";
		List<Answer> answers = new ArrayList<Answer>();
		JdbcTemplate template = new JdbcTemplate();
		answers = template.<Answer>query(sql, rs -> {
			return new Answer(rs.getLong("answerid"), rs.getString("writer"), rs.getString("contents"),
					rs.getDate("createddate"), rs.getLong("questionid"));
		}, String.valueOf(questionId));
		return answers;
	}
	
	public void deleteByAnswerId(long answerId) {
		String sql = "DELETE FROM ANSWERS WHERE answerid=?";
		JdbcTemplate template = new JdbcTemplate();
		template.update(sql, String.valueOf(answerId));
	}
}
