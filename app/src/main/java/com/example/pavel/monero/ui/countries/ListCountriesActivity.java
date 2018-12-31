package com.example.pavel.monero.ui.countries;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.anchorfree.hydrasdk.api.data.Country;
import com.example.pavel.monero.Management.ManagerFile;
import com.example.pavel.monero.R;
import com.example.pavel.monero.ui.countries.list.AdapterListCountries;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCountriesActivity extends AppCompatActivity implements IViewCountries,
        AdapterListCountries.ClickOnCountry {

    @BindView(R.id.recycler_view_countries) RecyclerView recyclerViewCountries;
    private                     IPresenterListCountries presenterListCountries;

    public static Intent START(Context context) {
        return new Intent(context, ListCountriesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_countries);
        ButterKnife.bind(this);
        presenterListCountries = new ListCountriesPresenter();
        presenterListCountries.addView(this);
        presenterListCountries.getCountries();
    }

    @Override
    public void showListCountries(List<Country> countryList) {
        AdapterListCountries adapterListCountries;
        adapterListCountries = new AdapterListCountries(countryList, new ManagerFile(this));
        adapterListCountries.addCallback(this);
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCountries.setAdapter(adapterListCountries);
    }

    @Override
    public void showErrorList(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callback(String countryName) {
        Toast.makeText(this, countryName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("Countries", countryName);
        setResult(RESULT_OK, intent);
    }
}
