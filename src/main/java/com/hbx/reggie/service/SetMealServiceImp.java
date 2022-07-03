package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.mapper.SetMealMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class SetMealServiceImp extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
}
