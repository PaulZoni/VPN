package com.example.pavel.monero.ui;

import android.annotation.SuppressLint;
import com.anchorfree.hydrasdk.HydraSdk;
import com.example.pavel.monero.Management.IManagementFile;
import com.example.pavel.monero.Management.IManagementVPN;
import com.example.pavel.monero.Management.ManagerVPN;
import com.example.pavel.monero.utils.Constants;


public class PresenterVPN implements InterfaceVPN<ViewVPN> {

    private final String    FILENAME = "Countries.txt";
    private String                   countryStart = "";
    private boolean                    loginIs = false;
    private boolean                 vpnIsStart = false;
    private ViewVPN                            viewVPN;
    private IManagementVPN                  managerVpn;
    private IManagementFile             managementFile;

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
        managerVpn.doAuthentication((String resultAuthentication) -> {
            loginIs = resultAuthentication.equals("Logged in successfully");
            viewVPN.messageLoginIs(resultAuthentication);
        });
    }

    @Override
    public void optimalCountries() {
        if (loginIs) {
            viewVPN.startLoader();
            managerVpn.startWithCountries(countryStart, this::checkStartResult);
        } else {
            viewVPN.showMessageErrorListCountries();
            viewVPN.changeTextOnButtonStart("START");
            vpnIsStart = false;
            showStopTraffic();
        }
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
        clearInfoRow();
        viewVPN.stopIndicator();
        viewVPN.startLoader();
        countryStart = country;
        managerVpn.stopVPN((answer) -> {
            viewVPN.showToastVpnStop();
            managerVpn.startWithCountries(country, this::checkStartResult);
        });
    }

    @Override
    public boolean isVpnIsStart() {
        return vpnIsStart;
    }

    @Override
    public void stopVpn() {
        viewVPN.changeTextOnButtonStart("START");
        viewVPN.showToastVpnStop();
        viewVPN.stopIndicator();
        clearInfoRow();
        vpnIsStart = false;
        managerVpn.stopVPN((result) -> showStopTraffic());
    }

    private void checkStartResult(String startResult) {
        viewVPN.outInfo(startResult, true);
        if (startResult.equals(ManagerVPN.VPN_CONNECTED)){
            viewVPN.showTargetLocation(countryStart);
            viewVPN.startIndicator();
            viewVPN.changeTextOnButtonStart("STOP");
            showTraffic();
            vpnIsStart = true;
        } else{
            viewVPN.stopLoader();
            viewVPN.showTargetLocation("error");
            viewVPN.changeTextOnButtonStart("START");
            showStopTraffic();
            vpnIsStart = false;
        }
    }

    private void clearInfoRow() {
        viewVPN.showTargetLocation("");
        viewVPN.outInfo("", false);
    }

    private void showTraffic() {
        managerVpn.vpnTraffic(((tx, rx) -> {
            if (vpnIsStart) viewVPN.showTraffic(tx, rx);
        }));
    }

    private void showStopTraffic() {
        managerVpn.stopTraffic(((l, l1) -> viewVPN.showTrafficAfterRemove(l, l1)));
    }
}
