package com.hbx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.common.BaseContext;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.ShoppingCart;
import com.hbx.reggie.service.DishFlavorService;
import com.hbx.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCarts);
    }
    @PostMapping("add")
    public R<String> add(@RequestBody  ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        if(dishId!=null){
            wrapper.eq(ShoppingCart::getDishId,dishId);
        }else{
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        //查看是否已经添加过这种商品
        ShoppingCart one = shoppingCartService.getOne(wrapper);
        if(one!=null){
            //数量加1
            Integer number = one.getNumber();
            one.setNumber(number+1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCartService.save(shoppingCart);
        }

        return R.success("添加购物车成功");
    }
    @DeleteMapping("clean")
    public R<String> clean(ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        shoppingCartService.remove(wrapper);
        return R.success("清空购物车成功");
    }

    @PostMapping("sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        if(shoppingCart.getDishId()!=null){
            wrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(wrapper);
        Integer number = one.getNumber()-1;
        if(number>0){
            one.setNumber(number);
            shoppingCartService.updateById(one);
        }
        else {
            wrapper.eq(ShoppingCart::getId,one.getId());
            shoppingCartService.remove(wrapper);
        }

        return R.success("成功");
    }

}
