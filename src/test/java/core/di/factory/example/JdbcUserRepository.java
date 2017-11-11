package core.di.factory.example;

import java.util.List;

import core.annotation.Repository;
import next.model.User;

@Repository
public class JdbcUserRepository implements UserRepository {

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
