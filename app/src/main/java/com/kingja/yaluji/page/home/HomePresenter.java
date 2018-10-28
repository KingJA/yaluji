package com.kingja.yaluji.page.home;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.model.entiy.ResultObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomePresenter implements HomeContract.Presenter {
    private UserApi mApi;
    private HomeContract.View mView;

    @Inject
    public HomePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getLunBoTuList() {
        mApi.getApiService().getLunBoTuList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<LunBoTu>>(mView) {
                    @Override
                    protected void onSuccess(List<LunBoTu> lunBoTuList) {
                        mView.onGetLunBoTuListSuccess(lunBoTuList);
                    }
                });
    }

    @Override
    public void getArticleList() {
        mApi.getApiService().getArticleList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<ArticleSimpleItem>>(mView) {
                    @Override
                    protected void onSuccess(List<ArticleSimpleItem> articleSimpleItemList) {
                        mView.onGetArticleListSuccess(articleSimpleItemList);
                    }
                });
    }


    @Override
    public void attachView(@NonNull HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}