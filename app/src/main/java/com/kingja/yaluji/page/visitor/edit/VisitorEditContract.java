package com.kingja.yaluji.page.visitor.edit;

import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Visitor;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface VisitorEditContract {
    interface View extends BaseView {
        void onEditVisitorSuccess(Visitor visitor);

    }

    interface Presenter extends BasePresenter<View> {
        void editVisitor(String touristId, String name, String mobile, String idcode);

    }
}
