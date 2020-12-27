package com.littlexx.shiro.demo.tips;

public class SuccessTip extends Tip {

    public SuccessTip() {
        super.code = 200;
        super.msg = "success";
    }

    public SuccessTip(String message) {
        super.code = 200;
        super.msg = message;
    }

    public SuccessTip(Object object) {
        super.code = 200;
        super.data = object;
    }

    public SuccessTip(String message, Object object) {
        super.code = 200;
        super.msg = message;
        super.data = object;
    }

}
