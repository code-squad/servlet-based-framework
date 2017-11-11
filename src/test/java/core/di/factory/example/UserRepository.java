package core.di.factory.example;

import java.util.List;

import next.model.User;

public interface UserRepository {

	public List<User> findAll();

}
