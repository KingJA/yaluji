package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/11/4 18:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Question {

    /**
     * id : a43d98c1bf314582b681b0f3a9f9f9ac
     * title : 问答主题测试
     * scenicId : 867b170d525e4237b542716557c89375
     * headimg : /upload/image/20180906/20180906154541_929.jpg
     * correctCount : 10
     * couponAmount : 1
     * couponPeriod : 10
     * userStatus : 1
     */

    private String id;
    private String title;
    private String scenicId;
    private String headimg;
    private int correctCount;
    private int couponAmount;
    private int couponPeriod;
    private int userStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponPeriod() {
        return couponPeriod;
    }

    public void setCouponPeriod(int couponPeriod) {
        this.couponPeriod = couponPeriod;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}
