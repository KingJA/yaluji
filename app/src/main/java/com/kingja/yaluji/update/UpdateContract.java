package com.kingja.yaluji.update;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.VersionInfo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface UpdateContract {
    interface View extends BaseView {
        void onGetVersionSuccess(VersionInfo versionInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getVersion(String version, int flag);

    }
}
