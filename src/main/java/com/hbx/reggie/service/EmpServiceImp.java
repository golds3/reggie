package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.Employee;
import com.hbx.reggie.mapper.EmpMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class EmpServiceImp extends ServiceImpl<EmpMapper, Employee> implements EmpService{
}
