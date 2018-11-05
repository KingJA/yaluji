package com.kingja.yaluji.service.initialize;



import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class)
public interface InitializeCompnent {
    void inject(InitializeService service);
}
