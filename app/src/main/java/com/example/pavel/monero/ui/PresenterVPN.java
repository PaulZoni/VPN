package com.example.pavel.monero.ui;

import android.annotation.SuppressLint;
import com.anchorfree.hydrasdk.HydraSdk;
import com.example.pavel.monero.Management.IManagementFile;
import com.example.pavel.monero.Management.IManagementVPN;
import com.example.pavel.monero.Management.ManagerVPN;
import com.example.pavel.monero.utils.Constants;


public class PresenterVPN implements InterfaceVPN<ViewVPN> {

    private final String          FILENAME = "Countries.txt";
    private String                         countryStart = "";
    private boolean                          loginIs = false;
    private ViewVPN viewVPN;
    private IManagementVPN managerVpn;
    private IManagementFile managementFile;

    public PresenterVPN (IManagementFile managementFile) {
        this.managementFile = managementFile;
        managerVpn = ManagerVPN.createManager();
    }

    @Override
    public void addView(ViewVPN view) {
        this.viewVPN = view;
    }

    @Override
    public void listAvailableCountries() {
        if (loginIs) viewVPN.startLActivityCountries();
        else viewVPN.showMessageErrorListCountries();
    }

    @Override
    public void authentication() {
        managerVpn.doAuthentication((String resultAuthentication)-> {
            loginIs = resultAuthentication.equals("Logged in successfully");
            viewVPN.messageLoginIs(resultAuthentication);
        });
    }

    @Override
    public void optimalCountries() {
        managerVpn.startWithCountries(countryStart, this::checkStartResult);
    }

    @SuppressLint("CheckResult")
    @Override
    public void checkCountryStart() {
        managementFile.readSelect(FILENAME, Constants.TYPE_STRING).subscribe((answer) -> {
            if (!answer.equals(Constants.ERROR_DOES_NOT_EXIST)) {
                viewVPN.showCountry(answer);
                countryStart = answer;
            } else {
                viewVPN.showCountry("Optimal");
                countryStart = HydraSdk.COUNTRY_OPTIMAL;
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveCountryState(String country) {
        managementFile.<Boolean>saveSelect(country, FILENAME).subscribe(System.out::println);
    }

    @Override
    public void reloadVpn(String country) {
        countryStart = country;
        managerVpn.stopVPN((answer) -> {
            viewVPN.showToastVpnStop();
            if (answer) managerVpn.startWithCountries(country, this::checkStartResult);
        });
    }

    private void checkStartResult(String startResult) {
        viewVPN.outInfo(startResult);
        if (startResult.equals(ManagerVPN.VPN_CONNECTED))
            viewVPN.showTargetLocation(countryStart);
        else
            viewVPN.showTargetLocation("error");
    }
}
