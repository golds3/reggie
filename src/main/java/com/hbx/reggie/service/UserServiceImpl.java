package com.hbx.reggie.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.User;
import com.hbx.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
