package com.hbx.reggie.common;

/**
 * @author 黄柏轩
 * @version 1.0
 * threadlocal工具类，获得session的数据
 */
public class BaseContext {
    private static final ThreadLocal<Long>  threadLocal= new ThreadLocal<>();
    public static void setId(Long id){
        threadLocal.set(id);
    }

    public static Long getId(){
        return threadLocal.get();
    }
}
