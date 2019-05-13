package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2019/5/13 23:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseItem {

    /**
     * id : cadfec4a9138443cb7fafe9b4fcfb3c0
     * title : 雅西漂流
     * scenicId : bd45bd044d0a4b459d3c722c63676890
     * headimg : /upload/image/20190422/20190422150202_805.jpg
     * likeCount : 10
     * couponUnitCount : 1000
     * couponAmount : 120
     * couponPeriod : 20
     * userStatus : 1
     * status : 0
     * statusText : 进行中
     */

    private String id;
    private String title;
    private String scenicId;
    private String headimg;
    private int likeCount;
    private int couponUnitCount;
    private int couponAmount;
    private int couponPeriod;
    private int userStatus;
    private int status;
    private String statusText;

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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCouponUnitCount() {
        return couponUnitCount;
    }

    public void setCouponUnitCount(int couponUnitCount) {
        this.couponUnitCount = couponUnitCount;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
