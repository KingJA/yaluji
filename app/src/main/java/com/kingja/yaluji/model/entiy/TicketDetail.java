package com.kingja.yaluji.model.entiy;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2018/7/11 13:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketDetail implements Serializable {


    /**
     * id : a3a2a9522f9b4db69f53872414ebb4db
     * visitDate : 2018-11-01 00:00:00至2018/11/30 23:59:59
     * discount : 8
     * isoneyuan : 0
     * status : 1
     * marketPrice : 100
     * buyPrice : 20
     * areaText : 浙江省温州市永嘉县
     * headImg : /upload/image/20180903/20180903222617_973.jpg
     * scenicid : 4a7bc01ab69b41ca922d73487e284d83
     * scenicBrief : 苍坡古村位于浙南永嘉县境内楠溪江上游岩头镇北面大山脚下，原名叫苍墩。始祖李岑为避战乱从福建长溪迁居于此，五代后周显德二年（955）开始营建，至今已1000多年历时。
     * visitTime : 8:30-17:00
     * useRemarks : 一张票抵用一次
     * remarks : 一张票抵用一次一张票抵用一次
     * buyLimit : 100
     * idcodeNeed : 1
     * startTime : 2018-10-31 16:16:02
     * endTime : 2018-11-29 23:59:59
     * phone : 0577-88218708
     * totalCount : 1000
     * sellCount : 0
     * ticketName : 苍坡古村抵用券
     * visitMethod : 景区售票窗口直接抵用
     */

    private String id;
    private String visitDate;
    private int discount;
    private int isoneyuan;
    private int status;
    private int marketPrice;
    private int buyPrice;
    private String areaText;
    private String headImg;
    private String scenicid;
    private String scenicBrief;
    private String visitTime;
    private String useRemarks;
    private String remarks;
    private int buyLimit;
    private int idcodeNeed;
    private String startTime;
    private String endTime;
    private String phone;
    private int totalCount;
    private int sellCount;
    private String ticketName;
    private String visitMethod;

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

    public String getUseRemarks() {
        return useRemarks;
    }

    public void setUseRemarks(String useRemarks) {
        this.useRemarks = useRemarks;
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

    public int getIdcodeNeed() {
        return idcodeNeed;
    }

    public void setIdcodeNeed(int idcodeNeed) {
        this.idcodeNeed = idcodeNeed;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
