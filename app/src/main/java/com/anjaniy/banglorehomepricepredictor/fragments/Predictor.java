package com.anjaniy.banglorehomepricepredictor.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anjaniy.banglorehomepricepredictor.R;

public class Predictor extends Fragment {

    private Button estimatePrice;
    private View view;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (inflater.inflate(R.layout.fragment_predictor,container,false));
        widgetSetup();
        estimatePrice.setOnClickListener(v -> {
            showProgressDialog();
        });
        return view;
    }

    private void widgetSetup() {
        estimatePrice = view.findViewById(R.id.estimate_price_btn);
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
