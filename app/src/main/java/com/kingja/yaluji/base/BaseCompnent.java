package com.kingja.yaluji.base;


import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.module.ActivityModule;
import com.kingja.yaluji.page.forgetpassword.ForgetPasswordActivity;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.modifypassword.ModifyPasswordActivity;
import com.kingja.yaluji.page.nickname.ModifyNicknameActivity;
import com.kingja.yaluji.page.order.list.TicketListFragment;
import com.kingja.yaluji.page.order.orderdetail.TicketDetailActivity;
import com.kingja.yaluji.page.personal.PersonalActivity;
import com.kingja.yaluji.page.register.RegisterActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(TicketListFragment target);
    void inject(TicketDetailActivity target);
    void inject(LoginActivity target);
    void inject(ForgetPasswordActivity target);
    void inject(RegisterActivity target);
    void inject(PersonalActivity target);
    void inject(ModifyNicknameActivity target);
    void inject(ModifyPasswordActivity target);
}
