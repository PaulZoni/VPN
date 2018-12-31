package com.example.pavel.monero.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.anchorfree.hydrasdk.vpnservice.ProcessUtils;
import com.example.pavel.monero.Management.ManagerFile;
import com.example.pavel.monero.R;
import com.example.pavel.monero.ui.countries.ListCountriesActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ViewVPN {

    @BindView(R.id.out_put)                     TextView out;
    @BindView(R.id.button_start)         Button button_start;
    @BindView(R.id.messageLoginIs)   TextView messageLoginIs;
    @BindView(R.id.login)                 Button loginButton;
    @BindView(R.id.country_button) ImageButton countryButton;
    @BindView(R.id.country_connect)  TextView countryConnect;
    @BindView(R.id.target_location)  TextView targetLocation;
    private InterfaceVPN<ViewVPN>                        vpn;
    private final int                        requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vpn = new PresenterVPN(new ManagerFile(this));
        vpn.addView(this);
        permissions();
        if (!ProcessUtils.isVpnProcess(this)) {
            //do your initialization stuff

        }
        initialListener();
        startWithCountry();
    }

    @Override
    public void messageLoginIs(String text) {
        messageLoginIs.setText(text);
    }

    @Override
    public void outInfo(String text) {
        showToast(text);
        out.setText(text);
    }

    @Override
    public void startLActivityCountries() {
        startActivityForResult(ListCountriesActivity.START(this), requestCode);
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

    private void permissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 123);
        } else {
            //TODO
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String country = data.getStringExtra("Countries");
        showCountry(country);
        vpn.saveCountryState(country);
        vpn.reloadVpn(country);

    }

    private void initialListener() {
        button_start.setOnClickListener((e) -> {
            vpn.optimalCountries();
            //startService(ForegroundService.START(this, this));//todo
        });
        loginButton.setOnClickListener((e) -> vpn.authentication());
        countryButton.setOnClickListener((e) -> vpn.listAvailableCountries());
    }

    private void startWithCountry() {
        vpn.checkCountryStart();
    }

    private void showToast(String infoText) {
        Toast.makeText(this, infoText, Toast.LENGTH_SHORT).show();
    }

}
