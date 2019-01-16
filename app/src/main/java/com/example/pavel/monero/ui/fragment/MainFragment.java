package com.example.pavel.monero.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pavel.monero.Management.ManagerFile;
import com.example.pavel.monero.R;
import com.example.pavel.monero.ui.InterfaceVPN;
import com.example.pavel.monero.ui.PresenterVPN;
import com.example.pavel.monero.ui.ViewVPN;
import com.example.pavel.monero.ui.countries.ListCountriesActivity;
import com.example.pavel.monero.ui.fragment.adapter.PlotAdapter;
import com.example.pavel.monero.ui.view.CustomIndicator;
import com.robinhood.spark.SparkView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements ViewVPN {

    @BindView(R.id.info)                            TextView out;
    @BindView(R.id.button_start)             Button button_start;
    @BindView(R.id.LoginIs)              TextView messageLoginIs;
    @BindView(R.id.login)                     Button loginButton;
    @BindView(R.id.country_button)     ImageButton countryButton;
    @BindView(R.id.country_connect)      TextView countryConnect;
    @BindView(R.id.location)             TextView targetLocation;
    @BindView(R.id.indicatorView)      CustomIndicator indicator;
    @BindView(R.id.bytes_transferred) TextView bytes_transferred;
    @BindView(R.id.bytes_received)       TextView bytes_received;
    @BindView(R.id.spark_view) SparkView               sparkView;
    private InterfaceVPN<ViewVPN>                            vpn;
    private final int                            requestCode = 1;
    private View                                            view;
    private PlotAdapter                              plotAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);
        vpn = new PresenterVPN(new ManagerFile(getContext()));
        vpn.addView(this);
        initialListener();
        startWithCountry();
        addAdapterToPlot();
        return view;
    }

    @Override
    public void messageLoginIs(String text) {
        messageLoginIs.setText(text);
    }

    @Override
    public void outInfo(String text, boolean isShowToast) {
        if (isShowToast)showToast(text);
        out.setText(text);
    }

    @Override
    public void startLActivityCountries() {
        startActivityForResult(ListCountriesActivity.START(getContext()), requestCode);
    }

    @Override
    public void showMessageErrorListCountries() {
        showToast("Log in");
    }

    @Override
    public void showCountry(String country) {
        countryConnect.setText(country);
    }

    @Override
    public void showToastVpnStop() {
        showToast("VPN stop");
    }

    @Override
    public void showTargetLocation(String location) {
        targetLocation.setText(location);
    }

    @Override
    public void startIndicator() {
        indicator.stopLoader();
        indicator.startIndicator();
    }

    @Override
    public void stopIndicator() {
        indicator.stopIndicator();
    }

    @Override
    public void startLoader() {
        indicator.startLoader();
    }

    @Override
    public void stopLoader() {
        indicator.stopLoader();
    }

    @Override
    public void changeTextOnButtonStart(String text) {
        button_start.setText(text);
    }

    @Override
    public void showTraffic(long tx, long rx) {
        bytes_transferred.setText(String.valueOf(tx));
        bytes_received.setText(String.valueOf(rx));
        plotAdapter.addTraffic(tx);
    }

    @Override
    public void showTrafficAfterRemove(long l, long l1) {
        String traffic = String.valueOf("All: " + l + ": " + l1);
        showToast(traffic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String country = data.getStringExtra("Countries");
        showCountry(country);
        vpn.saveCountryState(country);
        vpn.reloadVpn(country);
    }

    private void initialListener() {
        button_start.setOnClickListener((e) -> {
            startVpn();
            //startService(ForegroundService.START(this, this));//todo
        });
        loginButton.setOnClickListener((e) -> vpn.authentication());
        countryButton.setOnClickListener((e) -> vpn.listAvailableCountries());
    }

    private void startVpn() {
        if (vpn.isVpnIsStart()) vpn.stopVpn();
        else vpn.optimalCountries();
    }

    private void startWithCountry() {
        vpn.checkCountryStart();
    }

    private void showToast(String infoText) {
        Toast.makeText(getContext(), infoText, Toast.LENGTH_SHORT).show();
    }

    private void addAdapterToPlot() {
        ArrayList<Long> arrayList = new ArrayList<>();
        plotAdapter = new PlotAdapter(arrayList);
        sparkView.setAdapter(plotAdapter);
    }
}
