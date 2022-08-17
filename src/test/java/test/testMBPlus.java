package test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.ReggieApplication;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.Employee;
import com.hbx.reggie.service.DishService;
import com.hbx.reggie.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void y(){
        int target=3;
        int a=1;
        t(target-a);
    }

    public void t(int target){
        Assertions.assertEquals(target,3);
//        log.info("{}",target);
    }
    @ParameterizedTest
    @ValueSource(ints={25})
    public void translateNum(int num) {
//        Assertions.assertTrue(num==25);
//        if(num<10) return 1;
        String s =String.valueOf(num);
//        int[] dp = new int[s.length()+1];
//        dp[0]=1;
//        dp[1]=1;
//        int i=2;
        int n = 2;
        Assertions.assertEquals((s.charAt(0)-'0'+s.charAt(1)-'0'),25);
//        for(int i = 2;i<dp.length;i++){
//            if(s.charAt(i-2)>=1&&s.charAt(i-2)<3&&s.charAt(i-1)>=0&&s.charAt(i-1)<6)
//                dp[i] = dp[i-1]+dp[i-2];
//            else dp[i] = dp[i-1];
//        }

//        return dp[s.length()];
     }
}
