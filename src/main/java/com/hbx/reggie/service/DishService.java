package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dto.DishDto;

/**
 * @author 黄柏轩
 * @version 1.0
 */
public interface DishService extends IService<Dish> {
    void saveDishAndFlavor(DishDto dishDto);
    DishDto selectDishAndFlavor(Long id);
    void updateDishAndFlavor(DishDto dishDto);
}
