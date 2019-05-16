package com.kingja.yaluji.model.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2019/5/15 22:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseDetail {

    /**
     * likeProgressList : []
     * currentDateTime : 2019-05-15 22:35:39
     * alreadyLikeCount : 0
     * remainLikeCount : 10
     * endDateTime : 2019-05-15 23:48:27
     * status : 1
     */


    private List<PraiseHeadImg> likeProgressList;
    /*用户集赞进行中*/
    private String currentDateTime;
    private int alreadyLikeCount;
    private int remainLikeCount;
    private String endDateTime;
    private String h5ShareUrl;
    private int status;
    /*用户集赞成功*/
    private String title;
    private int couponAmount;
    private int couponUnitCount;
    private String couponUsePeriod;
    /*用户集赞超过24小时失败*/
    /*活动已结束但未完成集赞*/
    private String message;

    public String getH5ShareUrl() {
        return h5ShareUrl;
    }

    public void setH5ShareUrl(String h5ShareUrl) {
        this.h5ShareUrl = h5ShareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponUnitCount() {
        return couponUnitCount;
    }

    public void setCouponUnitCount(int couponUnitCount) {
        this.couponUnitCount = couponUnitCount;
    }

    public String getCouponUsePeriod() {
        return couponUsePeriod;
    }

    public void setCouponUsePeriod(String couponUsePeriod) {
        this.couponUsePeriod = couponUsePeriod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public int getAlreadyLikeCount() {
        return alreadyLikeCount;
    }

    public void setAlreadyLikeCount(int alreadyLikeCount) {
        this.alreadyLikeCount = alreadyLikeCount;
    }

    public int getRemainLikeCount() {
        return remainLikeCount;
    }

    public void setRemainLikeCount(int remainLikeCount) {
        this.remainLikeCount = remainLikeCount;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PraiseHeadImg> getLikeProgressList() {
        return likeProgressList;
    }

    public void setLikeProgressList(List<PraiseHeadImg> likeProgressList) {
        this.likeProgressList = likeProgressList;
    }
}
