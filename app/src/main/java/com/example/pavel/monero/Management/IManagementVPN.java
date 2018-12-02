package com.example.pavel.monero.Management;

import com.anchorfree.hydrasdk.api.data.Country;

import java.util.List;

public interface IManagementVPN {

    interface CallbackCountry {
        void getCountry(List<Country> countryList, String error);
    }

    interface CallbackAuthentication {
        void getResultAuthentication(String resultAuthentication);
    }

    interface CallbackStartResult {
        void getStartResult(String startResult);
    }


    void getListAvailableCountries(CallbackCountry callback);
    void doAuthentication(CallbackAuthentication callback);
    void startWithCountries(String country, CallbackStartResult callback);
}
