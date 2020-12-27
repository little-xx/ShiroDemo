package com.littlexx.shiro.demo.tips;

import lombok.Data;

@Data
public abstract class Tip {

    protected Integer code;

    protected String msg;

    protected Object data;
}
