package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/7/10 13:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HotSearch {
    private String id;
    private String keyword;
    private int ishighlight;
    private int sort;
    private String ishighlightText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getIshighlight() {
        return ishighlight;
    }

    public void setIshighlight(int ishighlight) {
        this.ishighlight = ishighlight;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIshighlightText() {
        return ishighlightText;
    }

    public void setIshighlightText(String ishighlightText) {
        this.ishighlightText = ishighlightText;
    }
}
