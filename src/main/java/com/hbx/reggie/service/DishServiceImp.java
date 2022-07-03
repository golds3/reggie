package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class DishServiceImp extends ServiceImpl<DishMapper, Dish> implements DishService {
}
