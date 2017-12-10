package com.lph.widget.navigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lph.widget.R;

/**
 * Created by Administrator on 2017/12/10 0010.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tvTitle = (TextView) inflater.inflate(R.layout.fragment_base_text, container, false);
        tvTitle.setText(getTitle());
        return tvTitle;
    }

    protected abstract String getTitle();
}
