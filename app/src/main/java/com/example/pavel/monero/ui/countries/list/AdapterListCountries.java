package com.example.pavel.monero.ui.countries.list;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import com.anchorfree.hydrasdk.api.data.Country;
import com.example.pavel.monero.Management.IManagementFile;
import com.example.pavel.monero.R;
import com.example.pavel.monero.utils.Constants;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AdapterListCountries extends RecyclerView.Adapter<CountriesViewHolder> {

    private List<Country> countryList;
    private IManagementFile managementFile;
    private static final String FILENAME = "file";
    private boolean[] checkState;
    private ClickOnCountry clickOnCountry;

    public interface ClickOnCountry {
        void callback(String countryName);
    }

    public AdapterListCountries (List<Country> countryList, IManagementFile managementFile) {
        this.managementFile = managementFile;
        this.countryList = countryList;
        checkState = new boolean[countryList.size()];
        readCheckBoxSate();
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        holder.getTextView().setText(countryList.get(position).getCountry());
        holder.getRadioButton().setChecked(checkState[position]);
        listenerPosition(holder.getRadioButton(), position);
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    private void listenerPosition(RadioButton checkBox, int position) {
        checkBox.setOnClickListener(v -> {

            for (int i = 0; i < checkState.length; i++) {
                checkState[i] = false;
            }

            checkState[position] = true;

            clickOnCountry.callback(countryList.get(position).getCountry());

            managementFile.saveSelect(position, FILENAME)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((s) -> notifyDataSetChanged());
        });
    }


    @SuppressLint("CheckResult")
    private void readCheckBoxSate() {
        managementFile.readSelect(FILENAME, Constants.TYPE_INTAGER).subscribe((position) -> {
            if (!position.equals(Constants.ERROR_DOES_NOT_EXIST)) {
                int id = Integer.parseInt((String) position);
                checkState[id] = true;
            }
        });
    }

    public void addCallback(ClickOnCountry clickOnCountry) {
        this.clickOnCountry = clickOnCountry;
    }
}
