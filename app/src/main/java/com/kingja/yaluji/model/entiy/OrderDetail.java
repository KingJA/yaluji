package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/7/5 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetail {
    private String id;
    private int quantity;
    private int status;
    private Double payamount;
    private String subject;
    private String qrcodeurl;
    private String ticketcode;
    private String tourists;
    private String paymethodText;
    private String statusText;
    private String createdTime;
    private String creatorderNoedTime;
    private String from;
    private String orderNo;
    private String paidAt;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getPayamount() {
        return payamount;
    }

    public void setPayamount(Double payamount) {
        this.payamount = payamount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public String getTicketcode() {
        return ticketcode;
    }

    public void setTicketcode(String ticketcode) {
        this.ticketcode = ticketcode;
    }

    public String getTourists() {
        return tourists;
    }

    public void setTourists(String tourists) {
        this.tourists = tourists;
    }

    public String getPaymethodText() {
        return paymethodText;
    }

    public void setPaymethodText(String paymethodText) {
        this.paymethodText = paymethodText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatorderNoedTime() {
        return creatorderNoedTime;
    }

    public void setCreatorderNoedTime(String creatorderNoedTime) {
        this.creatorderNoedTime = creatorderNoedTime;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }
}
