package com.anjaniy.banglorehomepricepredictor.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anjaniy.banglorehomepricepredictor.R;
import com.anjaniy.banglorehomepricepredictor.adapters.Saved_Predictions_Adapter;
import com.anjaniy.banglorehomepricepredictor.models.Prediction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class Saved_Predictions extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Prediction> predictions;
    private Saved_Predictions_Adapter adapter;
    private ProgressDialog dialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (inflater.inflate(R.layout.fragment_saved_predictions,container,false));
            showProgressDialog();
            swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
            recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_saved_predictions);
            recyclerView.setHasFixedSize(true);
            predictions = new ArrayList<>();
            proceed();

            swipeRefreshLayout.setOnRefreshListener (() -> {
                proceed();
                swipeRefreshLayout.setRefreshing(false);
            });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void proceed() {
        predictions.clear();
        GetSavedPredictions();
        recyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));
        adapter = new Saved_Predictions_Adapter(getActivity(),predictions);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void GetSavedPredictions(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database
                .collection("Predictions")
                .whereEqualTo("emailAddress", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())
                .addSnapshotListener((value, error) -> {
                    assert value != null;

                    if(error != null){
                        if(dialog.isShowing()){
                            dismissDialog();
                        }
                        Log.e("Error", error.getLocalizedMessage());
                        return;
                    }

                    for(DocumentChange documentChange : value.getDocumentChanges()){
                        if(documentChange.getType() == DocumentChange.Type.ADDED){
                            predictions.add(documentChange.getDocument().toObject(Prediction.class));
                        }
                        adapter.notifyDataSetChanged();
                        if(dialog.isShowing()){
                            dismissDialog();
                        }
                    }
                });
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setContentView(R.layout.progress_dialog_main);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
