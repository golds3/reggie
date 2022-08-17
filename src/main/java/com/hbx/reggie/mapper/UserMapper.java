package com.hbx.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbx.reggie.dao.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
