package com.example.pavel.monero.ui;


public interface ViewVPN {
    void messageLoginIs(String text);
    void outInfo(String text);
    void startLActivityCountries();
    void showMessageErrorListCountries();
    void showCountry(String country);
    void showToastVpnStop();
    void showTargetLocation(String location);
}
