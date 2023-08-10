package org.example.repository;

import org.example.model.User;

import java.util.List;

public interface IUserDao {

    List<User> getUsers();

    User save(User user);

    User getUserByCredentials(String emailOrUsername, String password) throws Exception;
}
