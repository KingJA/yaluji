package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/10/30 22:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Article {

    /**
     * id : 9ac284cf351b4c69b0abe6e10fc4d1a6
     * title : 1112
     * headimg : /upload/image/20181024/20181024172233_291.png
     * periodDate : 2018年10月24日 下午05:29
     */

    private String id;
    private String title;
    private String headimg;
    private String periodDate;

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }
}
