package com.example.pavel.monero.ui;

public interface InterfaceVPN <T> {
    void addView(T view);
    void listAvailableCountries();
    void authentication();
    void optimalCountries();
    void checkCountryStart();
    void saveCountryState(String country);
    void reloadVpn(String country);
    boolean isVpnIsStart();
    void stopVpn();
}
