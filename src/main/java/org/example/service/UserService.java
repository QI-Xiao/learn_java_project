package org.example.service;

import org.example.model.User;
import org.example.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public User save(User user){
        return userDao.save(user);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserBYCredentials(String email, String password) throws Exception {
        return userDao.getUserByCredentials(email, password);
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }
}
