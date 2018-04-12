package next.service;

import next.dao.UserDao;
import next.model.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

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


}
