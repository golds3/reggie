package com.hbx.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.commenreturn.R;
import com.hbx.reggie.dao.Employee;
import com.hbx.reggie.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class Empcontroller {
    @Autowired
    private EmpService empService;


    @PostMapping("/login")
    public R<Employee> Emplogin(HttpSession session, @RequestBody Employee emp){
        //查询数据库是否右这个用户名
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,emp.getUsername());
        Employee one = empService.getOne(queryWrapper);
        if(one==null){
            return R.error("用户名不存在");
        }
        //比对密码是否一致
        String password = emp.getPassword();
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5.equals(one.getPassword())){
            return R.error("密码错误");
        }
        //查看员工当前状态是否为禁用
        if(one.getStatus()!=1){
            return R.error("账号已被禁止使用");
        }
        //允许登陆，保存到session
        session.setAttribute("id",one.getId());
         return R.success(one);
    }
    @PostMapping("/logout")
    public R<String> Emplogout(HttpSession session){
        //清楚session
        session.removeAttribute("id");
        return R.success("已退出账号");
    }
    @PostMapping
    public R<String> Empadd(HttpServletRequest request, @RequestBody Employee employee){
        String md5 = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(md5);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long id =(Long) request.getSession().getAttribute("id");
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        empService.save(employee);
        log.info("保存成功");
        return R.success("1");
    }




}
