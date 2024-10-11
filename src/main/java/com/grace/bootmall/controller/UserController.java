package com.grace.bootmall.controller;


import com.grace.bootmall.dto.UserRequest;
import com.grace.bootmall.model.User;
import com.grace.bootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody UserRequest userRequest) {
        Integer userId = userService.register(userRequest);
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody UserRequest userRequest) {
        userService.login(userRequest);
        User user  = userService.getUserByEmail(userRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
