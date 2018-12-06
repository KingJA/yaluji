package com.kingja.yaluji.page.ticket.location;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Ticket;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface LocationContract {
    interface View extends BaseView {
        void onUploadLocationSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void uploadLocation(String lat, String lng);
    }
}
