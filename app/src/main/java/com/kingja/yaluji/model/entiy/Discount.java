package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/11/10 23:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Discount {
    private String discountName;
    private String discountCode;

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Discount(String discountName, String discountCode) {
        this.discountName = discountName;
        this.discountCode = discountCode;
    }
}
