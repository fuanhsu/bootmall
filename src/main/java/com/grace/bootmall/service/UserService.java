package com.grace.bootmall.service;

import com.grace.bootmall.dto.UserRequest;
import com.grace.bootmall.model.User;

public interface UserService {
    Integer register(UserRequest userRequest);
    User getUserById(Integer userId);
    void login(UserRequest userRequest);
    User getUserByEmail(String email);

}
