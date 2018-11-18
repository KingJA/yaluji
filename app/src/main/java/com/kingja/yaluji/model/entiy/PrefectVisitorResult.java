package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/11/16 0:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PrefectVisitorResult {

    /**
     * couponPeriod : 2018-11-16至2018-12-16
     * couponName : 华顶国家森林公园 100元抵用券
     * couponAmount : 100
     * couponLimit : 1
     */

    private String couponPeriod;
    private String couponName;
    private int couponAmount;
    private int couponLimit;

    public String getCouponPeriod() {
        return couponPeriod;
    }

    public void setCouponPeriod(String couponPeriod) {
        this.couponPeriod = couponPeriod;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(int couponLimit) {
        this.couponLimit = couponLimit;
    }
}
