package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/11/8 0:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AnswerResult {

    /**
     * correctStatus : 0
     */

    private String couponName;
    private int couponLimit;
    private String couponAmount;
    private String couponPeriod;
    private int rebornTimes;
    private int correctStatus;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(int couponLimit) {
        this.couponLimit = couponLimit;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponPeriod() {
        return couponPeriod;
    }

    public void setCouponPeriod(String couponPeriod) {
        this.couponPeriod = couponPeriod;
    }

    public int getRebornTimes() {
        return rebornTimes;
    }

    public void setRebornTimes(int rebornTimes) {
        this.rebornTimes = rebornTimes;
    }

    public int getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(int correctStatus) {
        this.correctStatus = correctStatus;
    }
}
