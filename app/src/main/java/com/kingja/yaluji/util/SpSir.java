package com.kingja.yaluji.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kingja.yaluji.base.App;
import com.kingja.yaluji.constant.Status;


/**
 * Description：TODO
 * Create Time：2016/8/15 13:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SpSir {
    private static final String TOKEN = "token";
    private static final String NICKNAME = "nickName";
    private static final String HEADIMG = "headImg";
    private static final String USERID = "userId";
    private static final String MOBILE = "mobile";
    private static final String HOTSEARCH = "HOTSEARCH";
    private static final String HistorySearch = "HistorySearch";
    private static final String ScenicType = "ScenicType";
    private static final String MsgCount = "MsgCount";
    private static final String City = "City";
    private static final String History_Keyword = "History_Keyword";
    private static final String EMPTY_STRING = "";
    private static final String NO_SHOW = "NO_SHOW";
    private static final String SOUND = "SOUND";
    private static final String SHAPE_PAGE = "SHAPE_PAGE";
    private static SpSir mSpSir;
    private SharedPreferences mSp;

    private SpSir() {
        mSp = App.getSp();
    }

    public static SpSir getInstance() {
        if (mSpSir == null) {
            synchronized (SpSir.class) {
                if (mSpSir == null) {
                    mSpSir = new SpSir();
                }
            }
        }
        return mSpSir;
    }

    /*================================GET================================*/

    public String getNickname() {
        return getString(NICKNAME);
    }

    public String getUserId() {
        return getString(USERID);
    }

    public String getToken() {
        return getString(TOKEN);
    }

    public String getMobile() {
        return getString(MOBILE);
    }

    public String getHeadImg() {
        return getString(HEADIMG);
    }

    public String getHotSearch() {
        return getString(HOTSEARCH);
    }

    public String getHistorySearch() {
        return getString(HistorySearch);
    }

    public String getScenicType() {
        return getString(ScenicType);
    }

    public String getCity() {
        return getString(City);
    }

    public String getHistoryKeyword() {
        return getString(History_Keyword);
    }

    public int getMsgCount() {
        return getInt(MsgCount, 0);
    }

    public boolean getNoShow() {
        return getBoolean(NO_SHOW, false);
    }

    public boolean getSound() {
        return getBoolean(SOUND, true);
    }

    public int getShapePage() {
        return getInt(SHAPE_PAGE, Status.SharePage.QUESTION_LIST);
    }

    /*================================PUT================================*/


    public void putNickName(String nickName) {
        putString(NICKNAME, nickName);
    }

    public void putToken(String token) {
        putString(TOKEN, token);
    }

    public void putMobile(String mobile) {
        putString(MOBILE, mobile);
    }

    public void putUserId(String userId) {
        putString(USERID, userId);
    }

    public void putHeadImg(String headImg) {
        putString(HEADIMG, headImg);
    }

    public void putHotSearch(String hotsearch) {
        putString(HOTSEARCH, hotsearch);
    }

    public void putScenicType(String scenicType) {
        putString(ScenicType, scenicType);
    }

    public void putCity(String city) {
        putString(City, city);
    }

    public void putNoShow(boolean noShow) {
        putBoolean(NO_SHOW, noShow);
    }

    public void putSound(boolean sound) {
        putBoolean(SOUND, sound);
    }

    public void putSharePage(int sharePage) {
        putInt(SHAPE_PAGE, sharePage);
    }

    public void addMsgCount() {
        int msgCount = getMsgCount();
        putInt(MsgCount, ++msgCount);
    }

    public void clearMsgCount() {
        putInt(MsgCount, 0);
    }

    public void putHistoryKeyword(String historyKeyword) {
        putString(History_Keyword, historyKeyword);
    }

    public void addHistorySearch(String historySearch) {
        if (TextUtils.isEmpty(historySearch)) {
            return;
        }
        for (String history : getHistorySearch().split("#")) {
            if (historySearch.equals(history)) {
                return;
            }
        }
        StringBuffer sb = new StringBuffer(historySearch).append("#").append(getHistorySearch());
        putString(HistorySearch, sb.toString());
    }

    public void clearHistorySearch() {
        putString(HistorySearch, "");
    }


    private void putString(String key, String value) {
        if (value != null) {
            mSp.edit().putString(key, value).apply();
        }
    }

    private String getString(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    private void putInt(String key, int value) {
        mSp.edit().putInt(key, value).apply();
    }

    private int getInt(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }

    private String getString(String key) {
        return mSp.getString(key, EMPTY_STRING);
    }

    private void putBoolean(String key, boolean value) {
        mSp.edit().putBoolean(key, value).apply();
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return mSp.getBoolean(key, defaultValue);
    }

    public void clearData() {
        putNickName(EMPTY_STRING);
        putUserId(EMPTY_STRING);
        putHeadImg(EMPTY_STRING);
        putToken(EMPTY_STRING);
        putMobile(EMPTY_STRING);
    }
}
