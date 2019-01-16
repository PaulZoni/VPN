package com.example.pavel.monero.Management;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ManagerConnected {

    private static ManagerConnected managerConnected;

    private ManagerConnected() {}

    public static ManagerConnected create() {
        if (managerConnected == null) managerConnected = new ManagerConnected();
        return managerConnected;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo;
        if (connectivityManager != null) {
            netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
