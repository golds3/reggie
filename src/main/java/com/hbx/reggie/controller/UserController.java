package com.hbx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.User;
import com.hbx.reggie.service.UserService;
import com.hbx.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        HashOperations<String, String, Object> redis = redisTemplate.opsForHash();

        if(StringUtils.isNotEmpty(phone)){
            log.info("10min内第{}次请求",(String)redis.get(phone+":count","count"));
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //保存到Redis
            //1.判断是否是第一次（10min内）发短信请求
            if(redis.get(phone+":count","count")==null){
                redis.put(phone+":count","count","1");
                redisTemplate.expire(phone+":count",60*10, TimeUnit.SECONDS);
                //判断是否超出限制
            }else if(Integer.parseInt((String)redis.get(phone+":count","count"))>4){
                return R.error("请10min后再发送");
            }else {
                redis.increment(phone+":count","count",1);
            }
            redis.put(phone+":code","code",code);
            redisTemplate.expire(phone+":code",60, TimeUnit.SECONDS);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功
            //判断是否已经过期
            HashOperations<String, String, Object> redis = redisTemplate.opsForHash();
            if(redis.get(phone+":code","code")==null){
                return R.error("验证码已失效");
            }

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("usrId",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

}
