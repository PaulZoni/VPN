package com.example.pavel.monero.ui;

import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.api.data.Country;
import com.example.pavel.monero.Management.IManagementVPN;
import com.example.pavel.monero.Management.ManagerVPN;
import java.util.List;

public class PresenterVPN implements InterfaceVPN<ViewVPN> {

    private ViewVPN viewVPN;
    private IManagementVPN managerVpn = new ManagerVPN();

    @Override
    public void addView(ViewVPN view) {
        this.viewVPN = view;
    }

    @Override
    public void listAvailableCountries() {
        managerVpn.getListAvailableCountries((List<Country> countryList, String error)-> {

        });
    }

    @Override
    public void authentication() {
        managerVpn.doAuthentication((String resultAuthentication)->
            viewVPN.messageLoginIs(resultAuthentication)
        );
    }

    @Override
    public void optimalCountries() {
        managerVpn.startWithCountries(HydraSdk.COUNTRY_OPTIMAL, (String startResult)->
            viewVPN.outInfo(startResult)
        );
    }
}
