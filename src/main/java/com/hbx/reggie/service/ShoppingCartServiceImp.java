package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.ShoppingCart;
import com.hbx.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class ShoppingCartServiceImp extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
