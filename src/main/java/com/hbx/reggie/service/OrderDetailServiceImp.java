package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.OrderDetail;
import com.hbx.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class OrderDetailServiceImp extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
