package com.hbx.reggie.exception;

import com.hbx.reggie.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 黄柏轩
 * @version 1.0
 */
//Duplicate entry 'asdasd' for key 'idx_username'
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> Duplicateid(SQLIntegrityConstraintViolationException ex){
        log.info("异常处理器开始工作");
        if(ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
            String key = s[2].replace("'","");
            String msg = key+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(AssociateException.class)
    public R<String> associate(AssociateException ex){
        return R.error(ex.getMessage());
    }
}
