package com.littlexx.shiro.demo.filter;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilter extends BasicHttpAuthenticationFilter {

}
