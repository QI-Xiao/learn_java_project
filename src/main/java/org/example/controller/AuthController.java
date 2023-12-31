package org.example.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.model.User;
import org.example.repository.exception.UserNotFoundException;
import org.example.service.JWTService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping(value = "")
    public ResponseEntity userLogin(@RequestBody User user) throws Exception {
        try {
            User u = userService.getUserBYCredentials(user.getEmail(), user.getPassword());
            if (u==null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtService.generateToken(u));

            return ResponseEntity.ok().body(response);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    //    old method
//    @PostMapping(value = "")
//    public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password) throws Exception {
//        try {
//            String digestPassword = DigestUtils.md5Hex(password.trim());
//            User user = userService.getUserBYCredentials(email, digestPassword);
//            return jwtService.generateToken(user);
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
