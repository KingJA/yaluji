package com.kingja.yaluji.model.entiy;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2018/7/3 16:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Visitor implements Serializable {
    protected String id;
    protected String name;
    protected String mobile;
    protected String idcode="";
    protected boolean isSelected;
    /**
     * 1默认 0非默认
     */
    protected int isdefault;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Visitor(String id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public Visitor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return null == name ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return null == mobile ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcode() {
        return null == idcode ? "" : idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }
}
