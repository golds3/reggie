package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbx.reggie.dao.Category;

/**
 * @author 黄柏轩
 * @version 1.0
 */
public interface CateService extends IService<Category> {
    void remove(Long id);

}
