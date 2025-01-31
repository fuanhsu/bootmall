package com.grace.bootmall.dao;

import com.grace.bootmall.dto.UserRequest;
import com.grace.bootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);
    Integer register(UserRequest userRequest);
    User getUserByEmail(String email);
}
