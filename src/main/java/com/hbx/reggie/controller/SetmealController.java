package com.hbx.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.Category;
import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.dto.SetmealDto;
import com.hbx.reggie.service.CateService;
import com.hbx.reggie.service.SetMealService;
import com.hbx.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private CateService cateService;

    @Cacheable(value = "setmealCache",key ="#setmeal.categoryId+'_'+#setmeal.status" )
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        wrapper.orderByDesc(Setmeal::getCreateTime);
        List<Setmeal> list = setMealService.list(wrapper);
        return R.success(list);
    }

    @CacheEvict(value = "setmealCache",allEntries = true)
    @PostMapping
    public R<String> saveDishAndSetmeal(@RequestBody SetmealDto setmealDto){
        setMealService.saveSetmealAndDish(setmealDto);
        return R.success("套餐新增成功");
    }

    @GetMapping("/page")
    public R<Page> listSetmeal(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setMealService.page(setmealPage);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> lsit = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            String categoryname = cateService.getById(categoryId).getName();
            setmealDto.setCategoryName(categoryname);
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(lsit);
        return R.success(setmealDtoPage);
    }

    @CacheEvict(value = "setmealCache",allEntries = true)
    @DeleteMapping
    public R<String> delSetMeal(Long ids){
        Setmeal setmeal = setMealService.getById(ids);
        Integer status = setmeal.getStatus();
        if(status==1){
            return R.error("启售的商品不能删除");
        }
        setMealService.delSetmealAndDish(ids);
        return R.success("删除成功");
    }

    @CacheEvict(value = "setmealCache",allEntries = true)
    @PostMapping("/status/{status}")
    public R<String> cutdownSetmeal(@PathVariable int status, Long ids){
        setMealService.cutdownstatus(status,ids);

        return R.success("成功");
    }




}
