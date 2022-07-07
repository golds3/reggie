package com.hbx.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.dao.SetmealDish;
import com.hbx.reggie.dto.SetmealDto;
import com.hbx.reggie.mapper.SetMealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
@Transactional
public class SetMealServiceImp extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveSetmealAndDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long categoryId = setmealDto.getCategoryId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(categoryId);
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void delSetmealAndDish(Long id) {
        Setmeal byId = this.getById(id);
        Long categoryId = byId.getCategoryId();
        this.removeById(id);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,categoryId);
        List<SetmealDish> list = setmealDishService.list(setmealDishLambdaQueryWrapper);
        setmealDishService.removeByIds(list);
    }

    @Override
    public void cutdownstatus(int status, Long id) {
        Setmeal setmeal = this.getById(id);
        setmeal.setStatus(status);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId,id);
        this.update(setmeal,setmealLambdaQueryWrapper);
    }


}
