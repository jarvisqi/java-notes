package com.softmax.oauth2.security.interceptors;

import com.softmax.oauth2.security.common.UserContext;
import com.softmax.oauth2.security.user.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器中获取用户信息并将用户信息放置在UserContext线程上下文中
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = (String) authentication.getPrincipal();
            LoginUser user = new LoginUser();
            user.setUsername(userName);
            UserContext.set(user);
        } catch (Exception e) {
            log.error("用户拦截器出错", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用（主要是用于进行资源清理工作）
     * 一定要在请求结束后调用remove清除当前线程的副本变量值，否则会造成内存泄漏
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
