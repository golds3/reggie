package com.hbx.reggie.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.Category;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.exception.AssociateException;
import com.hbx.reggie.mapper.CateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class CateServiceImp extends ServiceImpl<CateMapper, Category> implements CateService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;
    @Override
    public void remove(Long id) {

        //判断是否有菜品关联
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new AssociateException("有菜品关联，不允许删除");
        }
        //判断是否有套餐关联
        LambdaQueryWrapper<Setmeal> SetmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        SetmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setMealService.count(SetmealLambdaQueryWrapper);
        if(count1>0){
            throw new AssociateException("有套餐关联，不允许删除");
        }
        //没有关联，允许删除
        removeById(id);
    }
}
