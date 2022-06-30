package test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.ReggieApplication;
import com.hbx.reggie.dao.Employee;
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
    private EmpService empService;
    @Test
    public void testlam(){
//        List<Employee> admin = empService.lambdaQuery().eq(Employee::getUsername, "admin").list();
//        for (Employee a : admin) {
//            log.info("{}",a);
//        }
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,"admin");
        Employee one = empService.getOne(queryWrapper);
        log.info("{}",one);




    }

}
