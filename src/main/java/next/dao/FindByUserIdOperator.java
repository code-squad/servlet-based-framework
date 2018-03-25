package next.dao;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface FindByUserIdOperator {
    public User method(ResultSet rs) throws SQLException;
}
