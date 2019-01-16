package com.example.pavel.monero.ui.countries;

import com.anchorfree.hydrasdk.api.data.Country;

import java.util.List;

public interface IViewCountries {
    void showListCountries(List<Country> countryList);
    void showErrorList(String error);
    void startIndicator();
    void stopIndicator();
}
