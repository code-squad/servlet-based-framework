package next.dao;

import core.jdbc.KeyHolder;
import next.model.Answer;

public class AnswerDao {

    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new KeyHolder();
        // 자동생성한 id 값을 넣어주어야 함 (*)
        JdbcTemplate.update(sql, holder, holder.getId(), answer.getWriter(), answer.getContents(),
                answer.getCreatedDate(), answer.getQuestionId());

        return findById(answer.getAnswerId());
    }

    public Answer findById(Long id) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
        RowMapper<Answer> rm = rs -> new Answer(rs.getLong("answerId"), rs.getString(2),
                rs.getString(3), rs.getDate(4), rs.getLong(5));
        return JdbcTemplate.queryForObject(sql, rm,  id);
    }
}
