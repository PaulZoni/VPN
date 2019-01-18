package com.example.pavel.monero.di;

import android.app.Application;

import com.example.pavel.monero.ui.fragment.MainFragment;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ManagerModule.class})
public interface AppComponent {
    void inject(MainFragment mainFragment);

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(Application application);
    }
}
