package com.czf.computer.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义一个登陆拦截器
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测session对象中是否有uid，有就放行，没有就重定向到登陆界面
     * @param request   请求对象
     * @param response  相应对象
     * @param handler   处理器（url+controller的映射）
     * @return ture表示放行，false表示拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HttpServletRequest来获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null){
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
