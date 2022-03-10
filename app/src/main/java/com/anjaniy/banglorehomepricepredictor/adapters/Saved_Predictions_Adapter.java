package com.anjaniy.banglorehomepricepredictor.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjaniy.banglorehomepricepredictor.R;
import com.anjaniy.banglorehomepricepredictor.models.Prediction;

import java.util.ArrayList;

public class Saved_Predictions_Adapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<Prediction> predictions;
    private LayoutInflater layoutInflater;

    public Saved_Predictions_Adapter(Context context, ArrayList<Prediction> predictions){

        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.predictions = predictions;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.saved_prediction_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final ViewHolder viewHolder = (ViewHolder)holder;

        viewHolder.sqft.setText("Sqft Area: " + predictions.get(position).getSqft());
        viewHolder.bhk.setText("BHK: " + predictions.get(position).getBhk());
        viewHolder.bath.setText("Baths: " + predictions.get(position).getBath());
        viewHolder.balcony.setText("Balcony: " + predictions.get(position).getBalcony());
        viewHolder.location.setText("Location: " + predictions.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView sqft,bhk,bath,balcony,location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sqft = (TextView)itemView.findViewById(R.id.sqft_saved_prediction);
            bhk = (TextView)itemView.findViewById(R.id.bhk_saved_prediction);
            bath = (TextView)itemView.findViewById(R.id.bath_saved_prediction);
            balcony = (TextView)itemView.findViewById(R.id.balcony_saved_prediction);
            location = (TextView)itemView.findViewById(R.id.location_saved_prediction);
        }
    }
}
