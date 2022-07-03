package test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.ReggieApplication;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.Employee;
import com.hbx.reggie.service.DishService;
import com.hbx.reggie.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Slf4j
@SpringBootTest(classes = ReggieApplication.class)
public class testMBPlus {
    @Autowired
    private DishService dishService;
    @Test
    public void testlam(){
//        List<Employee> admin = empService.lambdaQuery().eq(Employee::getUsername, "admin").list();
//        for (Employee a : admin) {
//            log.info("{}",a);
//        }
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getName,"口味蛇");
        Dish one = dishService.getOne(queryWrapper);
        log.info("{}",one);




    }

}
