package next.dao;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface FindOperator {
    List<User> find(ResultSet rs) throws SQLException;
}
