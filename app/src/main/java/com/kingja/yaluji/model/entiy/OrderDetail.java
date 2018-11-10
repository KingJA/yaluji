package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/7/5 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetail {

    /**
     * id : d4fa9168061148af883b30ed3345e4b0
     * quantity : 1
     * status : 1
     * payamount : 20
     * subject : 苍坡古村抵用券|海南
     * qrcodeurl : /upload/ticketcode/154143308712585bbf193.png
     * ticketcode : 75182931
     * from : android
     * tourists : 马云  18868269007
     * informed : 1
     * travelDate :
     * useDate : 2018-11-01至2018-11-30
     * useRemarks : 一张票抵用一次
     * visitMethod : 景区售票窗口直接抵用
     * createdTime : 2018-11-05 23:51:27
     * paymethodText :
     * statusText : 待使用
     * orderNo : 85bbf193
     * paidAt : 2018-11-05 23:51:27
     */

    private String id;
    private int quantity;
    private int status;
    private int payamount;
    private String subject;
    private String qrcodeurl;
    private String ticketcode;
    private String from;
    private String tourists;
    private int informed;
    private String travelDate;
    private String useDate;
    private String useRemarks;
    private String visitMethod;
    private String createdTime;
    private String paymethodText;
    private String statusText;
    private String orderNo;
    private String paidAt;

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

    public int getPayamount() {
        return payamount;
    }

    public void setPayamount(int payamount) {
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTourists() {
        return tourists;
    }

    public void setTourists(String tourists) {
        this.tourists = tourists;
    }

    public int getInformed() {
        return informed;
    }

    public void setInformed(int informed) {
        this.informed = informed;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getUseRemarks() {
        return useRemarks;
    }

    public void setUseRemarks(String useRemarks) {
        this.useRemarks = useRemarks;
    }

    public String getVisitMethod() {
        return visitMethod;
    }

    public void setVisitMethod(String visitMethod) {
        this.visitMethod = visitMethod;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }
}
