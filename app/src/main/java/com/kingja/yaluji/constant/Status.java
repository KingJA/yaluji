package com.kingja.yaluji.constant;

/**
 * Description:TODO
 * Create Time:2018/7/13 15:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Status {
    public interface SellStatus {
        //在售
        int SELLING = 1;
        //待售
        int UNSELLING = 0;
        //售完
        int SELLOUT = 2;
        //结束
        int OVER = 3;
    }

    public interface PayType {
        //支付宝
        int ALIPAI = 2;
        //微信支付
        int WEIXINPAI = 1;
        //未选择
        int NOPAI = 0;
    }

    public interface ResultCode {
        //成功
        int SUCCESS = 0;
        //服务器错误
        int ERROR_SERVER = 1;
        //登录失效
        int ERROR_LOGIN_FAIL = -1;
    }

//    public interface OrderStatus {
//        //待支付
//        int WAIT_PAY = 0;
//        //待使用
//        int WAIT_USE = 1;
//        //已使用
//        int USED = 2;
//        //待出票
//        int WAIT_TICKET = 3;
//        //已过期
//        int OVER_TIME = 4;
//        //已取消
//        int CANCELED = 8;
//    }

    public enum OrderStatus implements CodeEnum {
        WAIT_PAY(0, "待支付"),
        WAIT_TICKET(1, "待使用"),
        USED(2, "已使用"),
        WAIT_USE(3, "待出票"),
        OVER_TIME(4, "已过期"),
        CANCELED(8, "已取消"),;
        private Integer code;
        private String msg;

        OrderStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public interface CodeEnum {
        Integer getCode();
    }
}
