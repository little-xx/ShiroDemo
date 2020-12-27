package com.littlexx.shiro.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Util {

    /**
     * md5加密
     * @param password
     * @return
     */
    private static String md5 (String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 生成16位随机盐
     * @return
     */
    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes(16).toHex();
    }

    /**
     * 生成新md5加密密码
     * @param password
     * @param salt
     * @return
     */
    public static String getMd5(String password, String salt) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            return null;
        }
        String newPassword = md5(password + salt);
        return newPassword;
    }
}
