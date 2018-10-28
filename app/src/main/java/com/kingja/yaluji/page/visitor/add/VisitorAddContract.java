package com.kingja.yaluji.page.visitor.add;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Visitor;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface VisitorAddContract {
    interface View extends BaseView {
        void onAddVisitorSuccess(Visitor visitor);

    }

    interface Presenter extends BasePresenter<View> {
        void addVisitor(String name, String mobile, String idcode);

    }
}
