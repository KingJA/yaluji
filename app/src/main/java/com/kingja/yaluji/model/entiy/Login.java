package com.kingja.yaluji.model.entiy;


/**
 * Description:TODO
 * Create Time:2018/4/6 20:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Login {

    /**
     * token : 5sd565asd55645wdac
     * uid : 123
     * expires_in : 1532158889
     */
    private String headImg;
    private String nickName;
    private String token;
    private String userId;
    private String mobile;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
