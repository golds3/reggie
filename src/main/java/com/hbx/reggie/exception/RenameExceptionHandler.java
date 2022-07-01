package com.hbx.reggie.exception;

import com.hbx.reggie.commenreturn.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 黄柏轩
 * @version 1.0
 */
//Duplicate entry 'asdasd' for key 'idx_username'
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class RenameExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> Duplicateid(SQLIntegrityConstraintViolationException ex){
        log.info("异常处理器开始工作");
        if(ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
            return R.error("账号"+s[2]+"已存在");
        }

        return R.error("未知错误");
    }
}
