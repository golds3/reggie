package com.hbx.reggie.controller;

import com.hbx.reggie.common.BaseContext;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.Orders;
import com.hbx.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@RestController
@RequestMapping("order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @RequestMapping("submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("success");
    }

}
