package com.example.pavel.monero.ui;

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

public class PresenterVPN implements InterfaceVPN<ViewVPN> {

    private ViewVPN viewVPN;

    @Override
    public void addView(ViewVPN view) {
        this.viewVPN = view;
    }

    @Override
    public void listAvailableCountries() {
        HydraSdk.countries(new Callback<List<Country>>() {
            @Override
            public void success(@NonNull List<Country> response) {
                //out.setText(response.toString());
            }

            @Override
            public void failure(@NonNull HydraException error) {
                //request failed
            }
        });
    }

    @Override
    public void authentication() {
        HydraSdk.login(AuthMethod.anonymous(), new Callback<User>() {
            @Override
            public void success(@NonNull User response) {
                viewVPN.messageLoginIs("Logged in successfully");
            }
            @Override
            public void failure(@NonNull HydraException error) {
                viewVPN.messageLoginIs("Fail to login");
            }
        });
    }

    @Override
    public void optimalCountries() {
        HydraSdk.startVPN(new SessionConfig.Builder()
                .withVirtualLocation(HydraSdk.COUNTRY_OPTIMAL)
                .withReason(TrackingConstants.GprReasons.M_UI)
                .build(), new Callback<ServerCredentials>() {
            @Override
            public void success(@NonNull ServerCredentials serverCredentials) {
                //VPN connected
                viewVPN.outInfo("VPN connected");
            }

            @Override
            public void failure(@NonNull HydraException e) {
                //Failed to connect vpn
                viewVPN.outInfo("Failed to connect vpn");
            }
        });
    }
}
