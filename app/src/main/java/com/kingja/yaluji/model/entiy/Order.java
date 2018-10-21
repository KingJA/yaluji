package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/7/5 13:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Order {
    //订单id
    private String id;
    //订单号
    private String orderNo;
    //景区头图
    private String headimg;
    //景区区域文本
    private String areaText;
    //购票数
    private Integer quantity;
    //订单标题
    private String subject;
    //订单状态0待支付 1待使用 2已使用 8已取消
    private Integer status;
    //订单金额
    private Double payamount;
    //出游日期yyyy-MM-dd
    private String visitDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPayamount() {
        return payamount;
    }

    public void setPayamount(Double payamount) {
        this.payamount = payamount;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
