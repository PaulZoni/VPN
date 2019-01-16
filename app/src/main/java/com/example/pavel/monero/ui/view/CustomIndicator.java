package com.example.pavel.monero.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.example.pavel.monero.R;
import com.wang.avi.AVLoadingIndicatorView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomIndicator extends FrameLayout {

    @BindView(R.id.indicator_view) AVLoadingIndicatorView indicatorView;
    @BindView(R.id.progress_bar)               ProgressBar progressBar;

    public CustomIndicator(Context context) {
        super(context);
        init();
    }

    public CustomIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.custom_indicator, this);
        ButterKnife.bind(this, view);
    }

    public void startIndicator() {
        indicatorView.show();
    }

    public void stopIndicator() {
        indicatorView.hide();
    }

    public void startLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoader() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
