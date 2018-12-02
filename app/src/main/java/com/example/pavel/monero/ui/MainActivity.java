package com.example.pavel.monero.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;
import com.anchorfree.hydrasdk.vpnservice.ProcessUtils;
import com.example.pavel.monero.R;
import com.example.pavel.monero.serves.ForegroundService;


public class MainActivity extends Activity implements ViewVPN {

    private TextView out;
    private Button button_start;
    private TextView messageLoginIs;
    private Button loginButton;
    private InterfaceVPN<ViewVPN> vpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpn = new PresenterVPN();
        vpn.addView(this);

        initialComponent();
        permissions();
        if (!ProcessUtils.isVpnProcess(this)) {
            //do your initialization stuff

        }
        initialListener();
    }

    private void initialComponent() {
        loginButton = findViewById(R.id.login);
        button_start = findViewById(R.id.button_start);
        out = findViewById(R.id.out_put);
        messageLoginIs = findViewById(R.id.messageLoginIs);

    }

    private void permissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 123);
        } else {
            //TODO
        }
    }

    private void initialListener() {
        button_start.setOnClickListener((e)-> {
            vpn.optimalCountries();
            //startService(ForegroundService.START(this, this));//todo
        });

        loginButton.setOnClickListener((e)-> {
            vpn.authentication();
        });
    }

    @Override
    public void messageLoginIs(String text) {
        messageLoginIs.setText(text);
    }

    @Override
    public void outInfo(String text) {
        out.setText(text);
    }
}
