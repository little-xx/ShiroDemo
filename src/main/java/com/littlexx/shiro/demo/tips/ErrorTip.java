package com.littlexx.shiro.demo.tips;

public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super.code = code;
        super.msg = message;
    }

    public ErrorTip(String message) {
        super.code = 500;
        super.msg = message;
    }
}
