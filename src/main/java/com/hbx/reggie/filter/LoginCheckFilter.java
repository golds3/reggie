package com.hbx.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.hbx.reggie.common.BaseContext;
import com.hbx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@WebFilter
@Slf4j
public class LoginCheckFilter implements Filter {
    private AntPathMatcher pathMatcher=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        String[] uris ={"/employee/login","/employee/logout","/backend/**","/front/**"};
        //判断是否为不需要拦截的路径
        if(check(uris,requestURI)){
            log.info("路径{}不需要处理，放行",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //拦截，判断是否已经登陆
        Long id =(Long) request.getSession().getAttribute("id");
        BaseContext.setId(id);
        if(id!=null){
            log.info("拦截路径{}，已登录，放行",requestURI);
            filterChain.doFilter(request,response);
        }
        else {
            //未登录，响应对应状态给前端，前端实现拦截跳转
            log.info("拦截路径{}，未登录，拦截", requestURI);
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }
    }
    public Boolean check(String[] uris,String URI){
        for (String uri : uris) {
            if (pathMatcher.match(uri,URI)){
                return true;
            }
        }
        return false;
    }
}
