package com.example.pavel.monero.ui.countries.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.pavel.monero.R;

public class CountriesViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private RadioButton checkBox;

    public CountriesViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_countries);
        checkBox = itemView.findViewById(R.id.box);
    }

    public RadioButton getRadioButton() {
        return checkBox;
    }

    public TextView getTextView() {
        return textView;
    }
}
