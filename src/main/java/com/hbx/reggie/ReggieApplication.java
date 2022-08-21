package com.hbx.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Slf4j
@ServletComponentScan(basePackages = "com.hbx.reggie.filter")
@EnableCaching
@SpringBootApplication
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("瑞吉外卖项目启动");
    }
}
