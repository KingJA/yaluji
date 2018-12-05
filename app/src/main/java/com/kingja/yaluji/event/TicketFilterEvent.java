package com.kingja.yaluji.event;

/**
 * Description:TODO
 * Create Time:2018/7/22 19:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketFilterEvent {
    private String areaId;
    private String productTypeId;
    private String useDates;
    private String buyLimit;
    private String discountOrder;

    public TicketFilterEvent(String areaId, String productTypeId, String useDates, String buyLimit, String
            discountOrder) {
        this.areaId = areaId;
        this.productTypeId = productTypeId;
        this.useDates = useDates;
        this.buyLimit = buyLimit;
        this.discountOrder = discountOrder;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public String getUseDates() {
        return useDates;
    }

    public String getBuyLimit() {
        return buyLimit;
    }

    public String getDiscountOrder() {
        return discountOrder;
    }
}
