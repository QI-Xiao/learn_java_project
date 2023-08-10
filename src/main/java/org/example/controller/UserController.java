package org.example.controller;

import org.example.model.Department;
import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping(value = "")
    public List<User> getUsers() {
        logger.info("This is ");

        return userService.getUsers();
    }

    @PostMapping(value = "")
    public void create(@RequestBody User user) {
        logger.info("create {}", user.getName());

        userService.save(user);
    }
}
