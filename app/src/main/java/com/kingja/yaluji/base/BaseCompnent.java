package com.kingja.yaluji.base;


import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.module.ActivityModule;
import com.kingja.yaluji.page.article.list.ArticleListActivity;
import com.kingja.yaluji.page.forgetpassword.ForgetPasswordActivity;
import com.kingja.yaluji.page.headimg.PersonalActivity;
import com.kingja.yaluji.page.home.HomeFragment;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.modifynickname.ModifyNicknameActivity;
import com.kingja.yaluji.page.modifypassword.ModifyPasswordActivity;
import com.kingja.yaluji.page.order.list.OrderListFragment;
import com.kingja.yaluji.page.order.orderdetail.TicketDetailActivity;
import com.kingja.yaluji.page.register.RegisterActivity;
import com.kingja.yaluji.page.visitor.add.VisitorAddActivity;
import com.kingja.yaluji.page.visitor.edit.VisitorEditActivity;
import com.kingja.yaluji.page.visitor.list.VisitorListActivity;
import com.kingja.yaluji.page.visitor.prefect.VisitorPrefectActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(OrderListFragment target);
    void inject(TicketDetailActivity target);
    void inject(LoginActivity target);
    void inject(RegisterActivity target);
    void inject(ForgetPasswordActivity target);
    void inject(PersonalActivity target);
    void inject(ModifyNicknameActivity target);
    void inject(HomeFragment target);
    void inject(ModifyPasswordActivity target);
    void inject(VisitorAddActivity target);
    void inject(VisitorEditActivity target);
    void inject(VisitorListActivity target);
    void inject(VisitorPrefectActivity target);
    void inject(ArticleListActivity target);
}
