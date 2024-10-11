package com.grace.bootmall.dao.impl;

import com.grace.bootmall.dao.UserDao;

import com.grace.bootmall.dto.UserRequest;
import com.grace.bootmall.model.User;
import com.grace.bootmall.rowmapper.OrderItemRowMapper;
import com.grace.bootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM user WHERE user_id = :userId ";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        List<User> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public Integer register(UserRequest userRequest) {
        String sql = "INSERT INTO user (email, password, created_date, last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate) ";
        Date now = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRequest.getEmail());
        map.put("password", userRequest.getPassword());
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM user WHERE email = :email ";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        List<User> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
        return userList.size() > 0 ? userList.get(0) : null;
    }
}
