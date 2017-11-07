package next.dao;
import java.sql.Timestamp;
import java.util.List;
import next.model.Answer;

public class AnswerDao {
	private static final AnswerDao answerDao = new AnswerDao();
	private AnswerDao() {}
	public static AnswerDao getInstance() {
		return answerDao;
	}
	public Answer insert(Answer answer) {
		// TODO Auto-generated method stub
		JdbcTemplate insertTemplate = JdbcTemplate.getInstance();
		return findByAnswerId(insertTemplate.update("INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)", (pstmt) -> {
			pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
            pstmt.setLong(4, answer.getQuestionId());
		}));
	}

	public Answer findByAnswerId(long insert) {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.queryForObject("SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?" , (rs) -> {
			return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getTimestamp("createdDate"), rs.getLong("questionId"));
		}, insert);
	}
	public List<Answer> findAllByQuestionId(long questionId) {
		// TODO Auto-generated method stub
		JdbcTemplate selectTemplate = JdbcTemplate.getInstance();
		return selectTemplate.query("SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc" , (rs) -> {
			return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getTimestamp("createdDate"), rs.getLong("questionId"));
		}, questionId);
	}
	public void delete(long answerId) {
		// TODO Auto-generated method stub
		JdbcTemplate deleteTemplate = JdbcTemplate.getInstance();
		deleteTemplate.update("DELETE FROM ANSWERS WHERE answerId=?" , answerId);
	}

}
