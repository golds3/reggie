package test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.ReggieApplication;
import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.Employee;
import com.hbx.reggie.service.DishService;
import com.hbx.reggie.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

     @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void test(){
        //String类型
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("city4","北京2");
        //设置过期时间，10s为例
        valueOperations.set("ha","1",10l,TimeUnit.SECONDS);
        valueOperations.setIfAbsent("ha","1");//false
        //获取
        valueOperations.get("city4");


        SetOperations setOperations = redisTemplate.opsForSet();
        //保存
        setOperations.add("myset","a","b");
        //取值
        Set myset = setOperations.members("myset");

        //删除
        setOperations.remove("mylist","a","b");


        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        //保存
        zSetOperations.add("myzset","a",7.0);//内部默认是根据score从小到大排序
        zSetOperations.add("myzset","b",8.0);
        zSetOperations.add("myzset","c",9.0);
        //取值
        Set myzset = zSetOperations.range("myzset", 0, -1);
        //修改score
        zSetOperations.incrementScore("myzset","b",1.0);
        //删除
        zSetOperations.remove("myzset","a","b");


        HashOperations hashOperations = redisTemplate.opsForHash();
        Set set = hashOperations.keys("phone");//set里面保存的是hashKey的名称
        List list = hashOperations.values("phone");//保存的是value
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("mylist","a");//一次存储一个
        listOperations.leftPushAll("mylist","a","b","c");//一次存储多个
        List mylist = listOperations.range("mylist", 0, -1);//获取
        Object mylist1 = listOperations.rightPop("mylist");//弹出一个
        Long mylist2 = listOperations.size("mylist");
        int length = mylist2.intValue();
//        for (int i = 0; i < length; i++) {//循环弹出
//            listOperations.rightPop("mylist");
//        }
        HyperLogLogOperations hyperLogLogOperations = redisTemplate.opsForHyperLogLog();


    }
    @Test
    public  void xx() {
        //获取所有的key
        Set<String> keys = redisTemplate.keys("*");
        //判断是否存在key
        Boolean mylist = redisTemplate.hasKey("mylist");
        //删除指定的key
        Boolean mylist1 = redisTemplate.delete("mylist");
        //获取指定key对应的value的数据类型
        DataType myset = redisTemplate.type("myset");

    }
}
