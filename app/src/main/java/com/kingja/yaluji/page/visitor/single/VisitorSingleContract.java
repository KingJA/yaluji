package com.kingja.yaluji.page.visitor.single;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Visitor;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface VisitorSingleContract {
    interface View extends BaseView {
        void onGetVisitorsSuccess(List<Visitor> visitors);

    }

    interface Presenter extends BasePresenter<View> {
        void getVisitors(Integer page, Integer pageSize);
    }
}
