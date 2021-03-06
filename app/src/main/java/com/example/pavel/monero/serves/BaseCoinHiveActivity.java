package com.example.pavel.monero.serves;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseCoinHiveActivity extends AppCompatActivity implements CoinHive.Callback {

    private CoinHive.Miner wvCoinHive;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        wvCoinHive = new CoinHive.Miner(this, this);
    }


    public void stopMining() {
        wvCoinHive.stopMining();
    }

    public void startMining() {
        wvCoinHive.startMining();
    }


    @Override
    protected void onDestroy() {
        if (CoinHive.getInstance().isLoggingEnabled()) {
            System.out.println("Mining stopped");
        }
        stopMining();
        super.onDestroy();
    }

    @Override
    public boolean isShowMining() {
        return false;
    }

    @Override
    public void onMiningStarted() {
        Toast.makeText(this, "onMiningStarted ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMiningStopped() {

    }

    @Override
    public void onRunning(double hashesPerSecond, long totalHashes, long acceptedHashes) {
        Toast.makeText(this, "totalHashes " +totalHashes, Toast.LENGTH_SHORT).show();
    }
}
