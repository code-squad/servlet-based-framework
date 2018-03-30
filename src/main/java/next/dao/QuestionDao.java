package next.dao;

import core.jdbc.KeyHolder;
import next.model.Question;

import java.util.List;

public class QuestionDao {

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder holder = new KeyHolder();
        // 자동생성한 id 값을 넣어주어야 함 (*)
        JdbcTemplate.update(sql, holder, holder.getId(), question.getWriter(), question.getTitle(),
                question.getContents(), question.getCreatedDate(), question.getCountOfComment());

        return findById(question.getQuestionId());
    }

    public Question findById(Long id) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId = ?";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString(2),
                rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6));

        return JdbcTemplate.queryForObject(sql, rm,  id);
    }

    public List<Question> findAll(){
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString(2),
                rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6));

        return JdbcTemplate.query(sql, rm);
    }
}
