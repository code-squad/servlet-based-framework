package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.dao.UserDao;
import next.model.User;

import java.util.List;

@Service
public class UserService {
    private UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void insert(User user){
        userDao.insert(user);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User findByUserId(String userId){
        return userDao.findByUserId(userId);
    }

    public void update(User user){userDao.update(user);}

}
