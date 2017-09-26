package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    // 拦截器执行之前,boolean决定是否拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 从cookie中获取token,从而获取用户信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        TbUser user = userService.getUserByToken(token);
        if (user == null) {
            // 转到登录页面
            response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN + "?redirect="
                    + request.getRequestURL());
            return false;
        }
        // 将登录的用户信息放入到request中 ,方便controller取
        request.setAttribute("user", user);
        return true;
    }

    // 拦截器执行之后,返回modelview之前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    // 返回视图model之后,
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {

    }

}
