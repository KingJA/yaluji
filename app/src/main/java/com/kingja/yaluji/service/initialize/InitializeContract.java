package com.kingja.yaluji.service.initialize;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.City;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.model.entiy.ScenicType;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface InitializeContract {
    interface View extends BaseView {
        void onGetHotSearch(List<HotSearch> hotSearches);
        void onGetScenicTypeSuccess(List<ScenicType> scenicTypes);
        void onGetCitySuccess(List<City> cities);
    }

    interface Presenter extends BasePresenter<View> {
        void getHotSearch(int limit);
        void getScenicType(String categoryId);
        void getCity();
    }
}
