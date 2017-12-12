package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import next.model.Answer;

public class AnswerDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	public Answer insert(Answer answer) {
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
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
		return findById(keyHolder.getId());
	}
	
	private Answer findById(long keyId) {
		//아이디 값을 가지고 디비에 저장된 질문을 가져오는듯
		return null;
	}
}
