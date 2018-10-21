package com.kingja.yaluji.model.entiy;

import com.google.gson.annotations.SerializedName;

/**
 * Description:TODO
 * Create Time:2018/7/13 17:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class WeixinPayResult {

    @SerializedName("package")
    private String packageStr;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String sign;
    private long timestamp;

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }


    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
