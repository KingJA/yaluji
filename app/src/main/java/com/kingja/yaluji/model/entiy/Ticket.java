package com.kingja.yaluji.model.entiy;

/**
 * Description:TODO
 * Create Time:2018/10/30 22:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Ticket {

    /**
     * id : c2791adf28b04043937b2495e036b878
     * productNo : YLJ2ff24bba
     * discount : 9
     * isoneyuan : 0
     * isRecommend : 0
     * status : 1
     * marketPrice : 100
     * buyPrice : 10
     * buyLimit : 1
     * totalCount : 100
     * sellCount : 0
     * areaText : 浙江省丽水市遂昌县
     * headImg : /upload/image/20180906/20180906154541_929.jpg
     * machineTotalCount : 0
     * machineSellCount : 0
     * statusText : 在抢
     * ticketName : 中国竹炭博物馆抵用券【测试】
     * startTime : 2018-11-01 17:11:45
     * endTime : 2018-12-30 23:59:59
     * useDate : 2018-12-01 00:00:00至2018/12/31 23:59:59
     */

    private String id;
    private String productNo;
    private double discount;
    private int isoneyuan;
    private int isRecommend;
    private int status;
    private int marketPrice;
    private int buyPrice;
    private int buyLimit;
    private int totalCount;
    private int sellCount;
    private String areaText;
    private String headImg;
    private int machineTotalCount;
    private int machineSellCount;
    private String statusText;
    private String ticketName;
    private String startTime;
    private String endTime;
    private String useDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getIsoneyuan() {
        return isoneyuan;
    }

    public void setIsoneyuan(int isoneyuan) {
        this.isoneyuan = isoneyuan;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
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

    public int getMachineTotalCount() {
        return machineTotalCount;
    }

    public void setMachineTotalCount(int machineTotalCount) {
        this.machineTotalCount = machineTotalCount;
    }

    public int getMachineSellCount() {
        return machineSellCount;
    }

    public void setMachineSellCount(int machineSellCount) {
        this.machineSellCount = machineSellCount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

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

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }
    public boolean isSellOut() {
        return sellCount == totalCount;
    }
}
