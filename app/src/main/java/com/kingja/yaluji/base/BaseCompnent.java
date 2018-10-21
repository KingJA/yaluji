package com.kingja.yaluji.base;


import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.module.ActivityModule;
import com.kingja.yaluji.page.order.list.TicketListFragment;
import com.kingja.yaluji.page.order.orderdetail.TicketDetailActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(TicketListFragment target);
    void inject(TicketDetailActivity target);
}
