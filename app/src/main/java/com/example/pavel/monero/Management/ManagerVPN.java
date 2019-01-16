package com.example.pavel.monero.Management;

import android.support.annotation.NonNull;
import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.SessionConfig;
import com.anchorfree.hydrasdk.api.AuthMethod;
import com.anchorfree.hydrasdk.api.data.Country;
import com.anchorfree.hydrasdk.api.data.ServerCredentials;
import com.anchorfree.hydrasdk.api.response.User;
import com.anchorfree.hydrasdk.callbacks.Callback;
import com.anchorfree.hydrasdk.callbacks.CompletableCallback;
import com.anchorfree.hydrasdk.callbacks.TrafficListener;
import com.anchorfree.hydrasdk.exceptions.HydraException;
import com.anchorfree.reporting.TrackingConstants;
import java.util.List;

public class ManagerVPN implements IManagementVPN {

    private static ManagerVPN managerVPN;
    public static String VPN_CONNECTED = "VPN connected";

    private ManagerVPN() {

    }

    public static ManagerVPN createManager() {
        if (managerVPN == null) managerVPN = new ManagerVPN();
        return managerVPN;
    }


    @Override
    public void getListAvailableCountries(CallbackCountry callback) {
        HydraSdk.countries(new Callback<List<Country>>() {
            @Override
            public void success(@NonNull List<Country> countries) {
                callback.getCountry(countries, null);
            }

            @Override
            public void failure(@NonNull HydraException e) {
                callback.getCountry(null, e.toString());
            }
        });
    }

    @Override
    public void doAuthentication(CallbackAuthentication callback) {
        HydraSdk.login(AuthMethod.anonymous(), new Callback<User>() {
            @Override
            public void success(@NonNull User response) {
                callback.getResultAuthentication("Logged in successfully");
            }
            @Override
            public void failure(@NonNull HydraException error) {
                callback.getResultAuthentication("Fail to login");
            }
        });
    }

    @Override
    public void startWithCountries(String country, CallbackStartResult callback) {
        startVpnWithSettings(country, callback);
    }

    @Override
    public void stopVPN(CallbackStop callbackStop) {
        HydraSdk.stopVPN(TrackingConstants.GprReasons.M_UI, new CompletableCallback() {
            @Override
            public void complete() {
                callbackStop.stopVpn(true);
            }

            @Override
            public void error(HydraException e) {
                callbackStop.stopVpn(false);
            }
        });
    }

    @Override
    public void vpnTraffic(CallbackTraffic callbackTraffic) {
        HydraSdk.addTrafficListener(callbackTraffic::getTraffic);
    }

    public void stopTraffic(CallbackRemoveTraffic callbackRemoveTraffic) {
        HydraSdk.removeTrafficListener(callbackRemoveTraffic::removeResult);
    }

    private void startVpnWithSettings(String country, CallbackStartResult callback) {
        HydraSdk.startVPN(new SessionConfig.Builder()
                .withVirtualLocation(country)
                .withReason(TrackingConstants.GprReasons.M_UI)
                .build(), new Callback<ServerCredentials>() {

            @Override
            public void success(@NonNull ServerCredentials serverCredentials) {
                callback.getStartResult(VPN_CONNECTED);
            }

            @Override
            public void failure(@NonNull HydraException e) {
                callback.getStartResult("Failed to connect vpn");
            }
        });
    }
}
