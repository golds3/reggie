package com.hbx.reggie.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Configuration
//@MapperScan("com.hbx.reggie.mapper")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor Interceptor = new MybatisPlusInterceptor();
        Interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return Interceptor;
    }
}
