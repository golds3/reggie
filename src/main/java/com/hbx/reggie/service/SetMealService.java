package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.dto.SetmealDto;

/**
 * @author 黄柏轩
 * @version 1.0
 */
public interface SetMealService extends IService<Setmeal> {
    void saveSetmealAndDish(SetmealDto setmealDto);
    void delSetmealAndDish(Long id);
    void cutdownstatus(int sattus,Long id);
}
