package com.kingja.yaluji.model.entiy;

import java.io.Serializable;

/**
 * Description:我的消息
 * Create Time:2018/4/18 15:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Message implements Serializable {
    private String id;
    private String title;
    private String content;
    private String createdAt;
    /**
     * 是否已读 1是 0否
     */
    private Integer isread;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }
}
