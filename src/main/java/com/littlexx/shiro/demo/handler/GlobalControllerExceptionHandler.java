package com.littlexx.shiro.demo.handler;


import com.littlexx.shiro.demo.tips.ErrorTip;
import com.littlexx.shiro.demo.tips.Tip;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Tip defaultErrorHandler(Exception e) {
        System.out.println(e.getMessage());
        return new ErrorTip("Permission denied");
    }


}
