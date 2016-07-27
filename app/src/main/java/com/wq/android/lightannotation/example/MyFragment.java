package com.wq.android.lightannotation.example;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wq.android.lightannotation.Injector;
import com.wq.android.lightannotation.OnClick;

/**
 * Created by qwang on 2016/7/21.
 */
public class MyFragment extends Fragment {

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        Injector.inject(this, root);
        return root;
    }

    @OnClick(R.id.btn_long_click_and_click)
    private void onClick(View v) {

    }
}
