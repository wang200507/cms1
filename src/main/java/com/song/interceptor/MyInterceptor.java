package com.song.interceptor;

import com.song.configuration.WebConfig;
import com.song.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
public class MyInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("------登录拦截------");
        //获取session
        HttpSession session = request.getSession(true);
        Object obj = session.getAttribute(Constants.USER_SESSION_KEY);
        String requetUrl = request.getRequestURI();
        if(requetUrl.contains("/admin/")){
            return true;
        }
        //判断用户ID是否存在，不存在就跳转到登录界面
        if(session.getAttribute(Constants.USER_SESSION_KEY) == null){
            logger.info("------:跳转到login页面！");
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }else{
            session.setAttribute(Constants.USER_SESSION_KEY, session.getAttribute(Constants.USER_SESSION_KEY));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
