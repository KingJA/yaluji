package com.kingja.yaluji.base;


import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.module.ActivityModule;
import com.kingja.yaluji.page.forgetpassword.ForgetPasswordActivity;
import com.kingja.yaluji.page.headimg.PersonalActivity;
import com.kingja.yaluji.page.home.HomeFragment;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.modifynickname.ModifyNicknameActivity;
import com.kingja.yaluji.page.modifypassword.ModifyPasswordActivity;
import com.kingja.yaluji.page.order.list.TicketListFragment;
import com.kingja.yaluji.page.order.orderdetail.TicketDetailActivity;
import com.kingja.yaluji.page.register.RegisterActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(TicketListFragment target);
    void inject(TicketDetailActivity target);
    void inject(LoginActivity target);
    void inject(RegisterActivity target);
    void inject(ForgetPasswordActivity target);
    void inject(PersonalActivity target);
    void inject(ModifyNicknameActivity target);
    void inject(HomeFragment target);
    void inject(ModifyPasswordActivity target);
}
