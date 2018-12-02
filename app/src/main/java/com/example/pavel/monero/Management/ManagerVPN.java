package com.example.pavel.monero.Management;

import android.support.annotation.NonNull;
import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.SessionConfig;
import com.anchorfree.hydrasdk.api.AuthMethod;
import com.anchorfree.hydrasdk.api.data.Country;
import com.anchorfree.hydrasdk.api.data.ServerCredentials;
import com.anchorfree.hydrasdk.api.response.User;
import com.anchorfree.hydrasdk.callbacks.Callback;
import com.anchorfree.hydrasdk.exceptions.HydraException;
import com.anchorfree.reporting.TrackingConstants;
import java.util.List;

public class ManagerVPN implements IManagementVPN {

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

    private void startVpnWithSettings(String country, CallbackStartResult callback) {
        HydraSdk.startVPN(new SessionConfig.Builder()
                .withVirtualLocation(country)
                .withReason(TrackingConstants.GprReasons.M_UI)
                .build(), new Callback<ServerCredentials>() {
            @Override
            public void success(@NonNull ServerCredentials serverCredentials) {
                callback.getStartResult("VPN connected");
            }

            @Override
            public void failure(@NonNull HydraException e) {
                callback.getStartResult("Failed to connect vpn");
            }
        });
    }
}
