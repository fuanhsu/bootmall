package com.grace.bootmall.service.impl;

import com.grace.bootmall.dao.UserDao;
import com.grace.bootmall.dto.UserRequest;
import com.grace.bootmall.model.User;
import com.grace.bootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user != null) {
            log.warn("user email {} 已存在" , userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPassword);
        return userDao.register(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void login(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user == null) {
            log.warn("user email {} 不存在" , userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        if (!user.getPassword().equals(hashedPassword)) {
            log.warn("user email {} 密碼 不符" , userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
