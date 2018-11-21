package com.example.pavel.monero.ui;

import android.Manifest;
import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
import android.os.Bundle;
/*import android.support.v4.app.ActivityCompat;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.anchorfree.hydrasdk.vpnservice.ProcessUtils;
import com.example.pavel.monero.serves.ForegroundService;
import com.example.pavel.monero.serves.Mainer;*/
import com.example.pavel.monero.R;
//import com.example.pavel.monero.ui.InterfaceVPN;

public class MainActivity extends Activity /*implements ViewVPN*/ {
    /*private Button start;
    private Button stop;
    private TextView out;
    private Button button_start;
    private TextView messageLoginIs;
    private Button loginButton;
    private InterfaceVPN<ViewVPN> vpn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*vpn = new PresenterVPN();
        vpn.addView(this);

        initialComponent();
        permissions();
        if (!ProcessUtils.isVpnProcess(this)){
            //do your initialization stuff
            //listAvailableCountries();
        }
        initialListener();*/
    }

   /* private void initialComponent() {
        loginButton = findViewById(R.id.login);
        stop = findViewById(R.id.stop);
        start = findViewById(R.id.start);
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
        start.setOnClickListener((e)-> {
            Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
            //JobIntentService.enqueueWork(this, Mainer.class, 12,Mainer.START(this, this));
            //startService(new Intent(this, ForegroundService.class));
        });

        stop.setOnClickListener((e)-> {
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
            stopService(Mainer.START(this, this));
        });

        button_start.setOnClickListener((e)-> {
            vpn.optimalCountries();
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
    }*/
}
