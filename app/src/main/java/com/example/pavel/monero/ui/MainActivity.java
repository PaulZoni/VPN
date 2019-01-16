package com.example.pavel.monero.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.pavel.monero.Management.ManagerConnected;
import com.example.pavel.monero.R;
import com.example.pavel.monero.ui.fragment.MainFragment;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ManagerConnected managerConnected = ManagerConnected.create();
        if (managerConnected.isConnected(this)) startApp();
        permissions();
    }

    private void startApp() {
        MainFragment mainFragment = new MainFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, mainFragment)
                .commit();
    }

    private void permissions() {
        int permissionCheck = ContextCompat
                .checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 123);
        } else {
            //TODO
        }
    }
}
