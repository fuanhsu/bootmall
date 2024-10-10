package com.grace.bootmall.dao.impl;

import com.grace.bootmall.dao.UserDao;

import com.grace.bootmall.model.User;
import com.grace.bootmall.rowmapper.OrderItemRowMapper;
import com.grace.bootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
