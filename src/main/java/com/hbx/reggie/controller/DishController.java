package com.hbx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.Category;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.DishFlavor;
import com.hbx.reggie.dto.DishDto;
import com.hbx.reggie.service.CateService;
import com.hbx.reggie.service.DishFlavorService;
import com.hbx.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@RestController
@RequestMapping("/dish")
@Slf4j
@EnableTransactionManagement
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CateService cateService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public R<String> saveDish(@RequestBody DishDto dishDto){
        dishService.saveDishAndFlavor(dishDto);
        return R.success("菜品添加成功");
    }
    @GetMapping("/page")
    public R<Page> listDish(int page,int pageSize,String name){
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name!=null,Dish::getName,name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage,dishLambdaQueryWrapper);
        //copy
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");
        List<Dish> records = dishPage.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = cateService.getById(categoryId);
            String categorname = category.getName();
            if (StringUtils.isNotEmpty(categorname)){
                dishDto.setCategoryName(categorname);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);

    }

    @GetMapping("/{id}")
    public R<DishDto> detailDish(@PathVariable("id") Long id){
        DishDto dishDto = dishService.selectDishAndFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateDishAndFlavor(dishDto);
        //清楚所有缓存数据
//        Set keys = redisTemplate.keys("dish*");
//        redisTemplate.delete(keys);
        //精确清理，只清理数据变化的那一类菜品缓存
        String key = "dish"+dishDto.getCategoryId()+":status1";
        redisTemplate.delete(key);
        return R.success("菜品修改成功");
    }

//    @GetMapping("/list")
//    public R<List> listDish(Dish dish){
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(Dish::getCategoryId,dish.getCategoryId());
//        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
//        dishLambdaQueryWrapper.orderByDesc(Dish::getSort);
//        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
//
//        return R.success(list);
//
//    }
    @GetMapping("/list")
    public R<List<DishDto>> listDish(Dish dish) {
        //redis 的key
        String key = "dish"+dish.getCategoryId()+":status"+dish.getStatus();
        //从缓存中获取数据
        List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        //判断是否缓存中是否有
        if(dishDtoList!=null){
            //有数据直接返回，不需要mysql
            return R.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, dish.getCategoryId());
        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        dishLambdaQueryWrapper.orderByDesc(Dish::getSort);
        List<Dish> records = dishService.list(dishLambdaQueryWrapper);
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long dishid = item.getId();
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId,dishid);
            List<DishFlavor> flavors = dishFlavorService.list(wrapper);
            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());

        //保存一份数据到redis
        redisTemplate.opsForValue().set(key,list,60, TimeUnit.MINUTES);
        return R.success(list);
    }



}
