package com.example.pavel.monero.di;

import android.app.Application;
import com.example.pavel.monero.Management.ManagerFile;
import com.example.pavel.monero.ui.fragment.adapter.PlotAdapter;
import java.util.ArrayList;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

    @Provides
    @Singleton
    PlotAdapter providePlotAdapter(Application application) {
        return new PlotAdapter(new ArrayList<Long>());
    }

    @Provides
    ManagerFile provideIManagementFile(Application application) {
        return new  ManagerFile(application);
    }
}