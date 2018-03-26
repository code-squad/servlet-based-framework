package next.dao;

import next.model.User;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface SetValuesConsumer {
    void setValuesForInsert(User user, PreparedStatement pstmt);

}
