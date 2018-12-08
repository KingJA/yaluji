package com.kingja.yaluji.constant;

import com.kingja.yaluji.R;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/13 11:10
 * 修改备注：
 */
public class Constants {
    public static final String BASE_URL = "http://api.ddchick.com";
    public static final int CORNER = 8;
    public static final int PAGE_SIZE = 500;
    public static final int THUMB_SIZE = 150;
    public static final int PAGE_SIZE_5 = 5;
    public static final int PAGE_FIRST = 1;
    public static final int PAGE_SIZE_100 = 500;
    public static final int DEAD_TIME = 20;
    public static final int AUTO_LUNBOTU = 3000;
    public static final int ORDER_STATUS_UNUSED = 1;
    public static final int ORDER_STATUS_ALL = 3;
    public static final String APPLICATION_NAME = "KingJA_SP";
    public static final String PLATFORM_ANDROID = "android";
    public static final String EXTRA_QUESTION = "EXTRA_QUESTION";
    public static final String OSNAME = "android";
    public static final int GRIDVIEW_IMG_COUNT = 3;
    public static final int GRIDVIEW_GIFT_COUNT = 4;
    public static final String APP_ID_WEIXIN = "wx18e668ee094be407";
    public static final String APP_ID_BUDLY = "f5e657251c";


    /*Extra*/

    public static final String EXTRA_OTHER_ACCOUNT_ID = "EXTRA_OTHER_ACCOUNT_ID";

    public interface Extra {
        String TicketStatus = "TicketStatus";
        String ProductId = "ProductId";
        String FromTitketDetail = "FromTitketDetail";
        String ArticleId = "articleId";
        String Keyword = "keyword";
        String PaperId = "PaperId";
        String TicketName = "ticketName";
        String BuyPrice = "buyPrice";
        String VisitDate = "visitDate";
        String VisitorName = "visitorName";
        String VisitorPhone = "visitorPhone";
        String Quantity = "quantity";
        String TicketDetail = "TicketDetail";
        String OrderId = "orderId";
        String BusinessType = "businessType";
        String TouristId = "touristId";
        String Idcode = "idcode";
    }

    public static final int BG_GRADIENTS[] = {R.drawable.gradient_pink,
            R.drawable.gradient_orange,
            R.drawable.gradient_green,
            R.drawable.gradient_blue,
            R.drawable.gradient_purple};

}
