package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.DishFlavor;
import com.hbx.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class DishFlavorServiceImp extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
