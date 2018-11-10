package com.kingja.yaluji.util;


import com.kingja.yaluji.constant.Status;

/**
 * Description:TODO
 * Create Time:2018/7/29 23:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EnumUtil {
    public static<T extends Status.CodeEnum>  T getByCode(Integer code, Class<T> enumClass) {
        for (T t : enumClass.getEnumConstants()) {
            if (code.equals(t.getCode())) {
                return t;
            }
        }
        return null;
    }
}
