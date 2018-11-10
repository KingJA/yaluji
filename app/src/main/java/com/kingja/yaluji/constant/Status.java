package com.kingja.yaluji.constant;

/**
 * Description:TODO
 * Create Time:2018/7/13 15:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Status {
    public interface SellStatus {
        //在抢
        int SELLING = 1;
        //抢完
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

    public interface TicketStatus {
        //待支付
        int WAIT_PAY = 0;
        //待使用
        int WAIT_USE = 1;
        //已使用
        int USED = 2;
        //全部
        int ALL = 3;
        //已过期
        int OVER_TIME = 4;
    }

    public enum OrderStatus implements CodeEnum {
        WAIT_PAY(0, "待支付"),
        WAIT_TICKET(1, "待使用"),
        USED(2, "已使用"),
        WAIT_USE(3, "待出票"),
        OVER_TIME(4, "已过期"),
        CANCELED(8, "已取消");
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

    //    integer,0复活 1答题 2成功 3失败
    public enum QuestionStatus implements CodeEnum {
        RELIFT(0, "去复活"),
        ANSWER(1, "去答题"),
        SUCCESS(2, "已领取"),
        FAIL(3, "失败");
        private Integer code;
        private String msg;

        QuestionStatus(Integer code, String msg) {
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

    public interface AnswerResult {
        int RIGHT = 0;
        int WRONG = 1;
        int SUCCESS = 2;
        int NO_RELIFE = 3;
    }

    public interface BusinsessType {
        //待支付
        String PROVIDER = "1";
        //待使用
        String ADVERTISEMENT = "2";
    }
}
