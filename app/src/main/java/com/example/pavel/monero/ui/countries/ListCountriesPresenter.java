package com.example.pavel.monero.ui.countries;

import com.anchorfree.hydrasdk.api.data.Country;
import com.example.pavel.monero.Management.IManagementVPN;
import com.example.pavel.monero.Management.ManagerVPN;

import java.util.List;

public class ListCountriesPresenter  implements IPresenterListCountries {

    private IViewCountries viewCountries;
    private IManagementVPN managementVPN = ManagerVPN.createManager();

    @Override
    public void getCountries() {
        managementVPN.getListAvailableCountries((List<Country> countryList, String error) -> {
            if (error == null) viewCountries.showListCountries(countryList);
            else viewCountries.showErrorList(error);
        });
    }

    @Override
    public void addView(IViewCountries view) {
        this.viewCountries = view;
    }
}
