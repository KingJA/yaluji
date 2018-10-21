package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/3/28 11:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Item {
    private int resId;
    private String title;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Item{" +
                "resId=" + resId +
                ", title='" + title + '\'' +
                '}';
    }
}
