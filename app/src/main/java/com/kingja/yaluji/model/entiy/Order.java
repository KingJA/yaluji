package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/10/28 21:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Order {

    /**
     * id : string,订单id
     * orderNo : string,订单号
     * quantity : integer,抢票数
     * subject : string,订单标题
     * status : integer,订单状态 1待使用 2已使用 8已取消
     * payamount : double,抵用金额
     * visitDate : string,使用期限
     * productid : string,优惠券id
     * statusText : string,状态的中文字典
     */

    private String id;
    private String orderNo;
    private int quantity;
    private String subject;
    private int status;
    private double payamount;
    private String visitDate;
    private String productid;
    private String statusText;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPayamount() {
        return payamount;
    }

    public void setPayamount(double payamount) {
        this.payamount = payamount;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
