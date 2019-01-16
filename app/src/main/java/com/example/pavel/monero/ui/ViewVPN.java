package com.example.pavel.monero.ui;


public interface ViewVPN {
    void messageLoginIs(String text);
    void outInfo(String text, boolean isShowToast);
    void startLActivityCountries();
    void showMessageErrorListCountries();
    void showCountry(String country);
    void showToastVpnStop();
    void showTargetLocation(String location);
    void startIndicator();
    void stopIndicator();
    void startLoader();
    void stopLoader();
    void changeTextOnButtonStart(String text);
    void showTraffic(long tx, long rx);
    void showTrafficAfterRemove(long l, long l1);
}
