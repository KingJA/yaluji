package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/11/18 10:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VersionInfo {

    /**
     * latestVersionName : 1.2.0
     * isLatest : 1
     * latestContent : 测试版本
     * latestVersionCode : 2018121201
     * latestDownload : http://api.ddchick.com/upload/android/yaluji154250919389016d59c2a163d425aa0b.apk
     * versionName : 1.2.0
     * versionCode : 2018121201
     * content : 测试版本
     * isForced : 1
     */

    private String latestVersionName;
    private int isLatest;
    private String latestContent;
    private int latestVersionCode;
    private String latestDownload;
    private String versionName;
    private int versionCode;
    private String content;
    private int isForced;

    public String getLatestVersionName() {
        return latestVersionName;
    }

    public void setLatestVersionName(String latestVersionName) {
        this.latestVersionName = latestVersionName;
    }

    public int getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(int isLatest) {
        this.isLatest = isLatest;
    }

    public String getLatestContent() {
        return latestContent;
    }

    public void setLatestContent(String latestContent) {
        this.latestContent = latestContent;
    }

    public int getLatestVersionCode() {
        return latestVersionCode;
    }

    public void setLatestVersionCode(int latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public String getLatestDownload() {
        return latestDownload;
    }

    public void setLatestDownload(String latestDownload) {
        this.latestDownload = latestDownload;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsForced() {
        return isForced;
    }

    public void setIsForced(int isForced) {
        this.isForced = isForced;
    }
}
