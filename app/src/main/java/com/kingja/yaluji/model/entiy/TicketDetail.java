package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/7/11 13:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketDetail {
    private String id;
    private String visitDate;
    private int discount;
    private int isoneyuan;
    private int status;
    private double marketPrice;
    private double buyPrice;
    private String areaText;
    private String headImg;
    private String scenicid;
    private String scenicBrief;
    private String visitTime;
    private String remarks;
    private int buyLimit;
    private int idcodeNeed;
    private int totalCount;
    private int sellCount;
    private String ticketName;
    private String visitMethod;
    private String startTime;
    private String endTime;
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getIdcodeNeed() {
        return idcodeNeed;
    }

    public void setIdcodeNeed(int idcodeNeed) {
        this.idcodeNeed = idcodeNeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getIsoneyuan() {
        return isoneyuan;
    }

    public void setIsoneyuan(int isoneyuan) {
        this.isoneyuan = isoneyuan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getScenicid() {
        return scenicid;
    }

    public void setScenicid(String scenicid) {
        this.scenicid = scenicid;
    }

    public String getScenicBrief() {
        return scenicBrief;
    }

    public void setScenicBrief(String scenicBrief) {
        this.scenicBrief = scenicBrief;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getVisitMethod() {
        return visitMethod;
    }

    public void setVisitMethod(String visitMethod) {
        this.visitMethod = visitMethod;
    }
}
