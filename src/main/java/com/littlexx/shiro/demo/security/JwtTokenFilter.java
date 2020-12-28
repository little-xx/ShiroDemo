package com.littlexx.shiro.demo.security;

import com.alibaba.fastjson.JSON;
import com.littlexx.shiro.demo.tips.ErrorTip;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器,用于每次外部对接口请求时对token的处理
 */
public class JwtTokenFilter extends BasicHttpAuthenticationFilter {


    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        System.out.println(httpServletRequest.getMethod());
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("isAccessAllowed");
        try {
            executeLogin(request, response);
        } catch (Exception e) {
            loginFailure(request, response, e);
            return false;
        }
        return true;
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        System.out.println("executeLogin");
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        JwtToken jwt = new JwtToken(authorization);
        getSubject(request, response).login(jwt);
        return true;
    }


    private void loginFailure(ServletRequest req, ServletResponse resp, Exception exception) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            String json = JSON.toJSONString(new ErrorTip(exception.getMessage()));
            httpServletResponse.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
