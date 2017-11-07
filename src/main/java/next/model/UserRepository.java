package next.model;

import core.annotation.Repository;
import core.jdbc.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository{
	
	public User findByUserId(String userId);

}
