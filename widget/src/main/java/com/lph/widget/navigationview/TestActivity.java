package com.lph.widget.navigationview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lph.widget.R;

public class TestActivity extends AppCompatActivity implements NavFragment.OnNavigationReselectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initNavigation();
    }

    private void initNavigation() {
        NavFragment navFragment = (NavFragment) getSupportFragmentManager().findFragmentById(R.id.navigation);
        navFragment.setup(this, getSupportFragmentManager(), R.id.fl_container, this);
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {

    }
}
