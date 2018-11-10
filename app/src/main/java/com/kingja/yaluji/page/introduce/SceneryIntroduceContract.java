package com.kingja.yaluji.page.introduce;

import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.SceneryIntroduce;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface SceneryIntroduceContract {
    interface View extends BaseView {
        void onGetSceneryIntroduceSuccess(SceneryIntroduce sceneryIntroduce);
    }

    interface Presenter extends BasePresenter<View> {
        void getSceneryIntroduce(String scenicId);

    }
}
