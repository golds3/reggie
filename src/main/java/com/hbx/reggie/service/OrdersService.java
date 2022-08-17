package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbx.reggie.dao.Orders;

/**
 * @author 黄柏轩
 * @version 1.0
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
