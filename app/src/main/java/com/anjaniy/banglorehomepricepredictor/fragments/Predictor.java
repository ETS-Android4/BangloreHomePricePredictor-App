package com.anjaniy.banglorehomepricepredictor.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.anjaniy.banglorehomepricepredictor.R;
import com.anjaniy.banglorehomepricepredictor.singleton.MySingleTon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Predictor extends Fragment {

    private EditText sqft;
    private Spinner bhk;
    private Spinner bath;
    private Spinner balcony;
    private Spinner locations;
    private Button estimatePrice;

    private String[] BHK_Array = new String[8];
    private String[] BATH_Array = new String[8];
    private String[] BALCONY_Array = new String[8];
    private String[] LOCATION_Names = new String[229];

    private String sqftSelected = "";
    private String bhkSelected = "";
    private String bathSelected = "";
    private String  balconySelected = "";
    private String locationSelected = "";

    private View view;
    private ProgressDialog dialog;

    private String Result = "";

    private final String LOCATIONS_URL = "https://bhpp-anjaniy.herokuapp.com/get_location_names";
    private final String ESTIMATE_PRICE_URL = "https://bhpp-anjaniy.herokuapp.com/predict_home_price";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (inflater.inflate(R.layout.fragment_predictor,container,false));
        widgetSetup();
        spinnerSetup();
        getLocations();

        bhk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bhkSelected = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bathSelected = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        balcony.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                balconySelected = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                locationSelected = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        estimatePrice.setOnClickListener(v -> {
            showProgressDialog();
            sqftSelected = sqft.getText().toString().trim();

            if(sqftSelected.isEmpty()){
                sqft.setError("Square Foot Area Is Required!");
                sqft.requestFocus();
                dismissDialog();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ESTIMATE_PRICE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    parseResponse(response);

                }
            }, error -> Log.d("tag", "onErrorResponse: " + error.getMessage())){

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("total_sqft",sqftSelected);
                    params.put("location",locationSelected);
                    params.put("bhk",bhkSelected);
                    params.put("bath",bathSelected);
                    params.put("balcony",balconySelected);

                    return params;
                }
            };
            MySingleTon.getInstance(getActivity()).addToRequestQueue(stringRequest);
        });
        return view;
    }

    private void getLocations() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LOCATIONS_URL, null, response -> {

            try {

                JSONArray jsonArray = response.getJSONArray("locations");
                int length = jsonArray.length();
                for(int i = 0 ; i < length ; i++){
                    LOCATION_Names[i] = jsonArray.getString(i).toUpperCase();

                    ArrayAdapter<String> spinnerArrayAdapter_LOCATIONS = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LOCATION_Names);
                    spinnerArrayAdapter_LOCATIONS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    locations.setAdapter(spinnerArrayAdapter_LOCATIONS);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("tag", "onErrorResponse: " + error.getMessage()));

        MySingleTon.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void spinnerSetup() {
        BHK_Array = getResources().getStringArray(R.array.bhk_array);
        BATH_Array =  getResources().getStringArray(R.array.bath_array);
        BALCONY_Array = getResources().getStringArray(R.array.balcony_array);

        ArrayAdapter<String> spinnerArrayAdapter_BHK = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, BHK_Array);
        spinnerArrayAdapter_BHK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        bhk.setAdapter(spinnerArrayAdapter_BHK);

        ArrayAdapter<String> spinnerArrayAdapter_BATH = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, BATH_Array);
        spinnerArrayAdapter_BATH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        bath.setAdapter(spinnerArrayAdapter_BATH);

        ArrayAdapter<String> spinnerArrayAdapter_BALCONY = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, BALCONY_Array);
        spinnerArrayAdapter_BALCONY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        balcony.setAdapter(spinnerArrayAdapter_BALCONY);
    }

    private void widgetSetup() {
        sqft = (EditText)view.findViewById(R.id.sqft);
        bhk = (Spinner)view.findViewById(R.id.bhk);
        bath = (Spinner)view.findViewById(R.id.bath);
        balcony = (Spinner)view.findViewById(R.id.balcony);
        locations = (Spinner)view.findViewById(R.id.location_names);
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

    private void parseResponse(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);

            Result = jsonObject.getString("estimated_price");

            dismissDialog();

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(requireActivity());

            builder.setMessage(Result + " " + "Lakhs")

                    .setCancelable(false)

                    //CODE FOR POSITIVE(YES) BUTTON: -
                    .setPositiveButton("Save Prediction", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ACTION FOR "YES" BUTTON: -
//                            String email_id = current_user.Get_User_Info(1);
//                            boolean flag = database.Insert_Data(sqft_selected,bhk_selected,bath_selected,balcony_selected,location_selected,Result);
//
//                            if(flag == true){
//                                Toast.makeText(getActivity(), "Successfully Saved!", Toast.LENGTH_LONG).show();
//                            }
//
//                            else{
//                                Toast.makeText(getActivity(), "Unable To Save!", Toast.LENGTH_LONG).show();
//                            }

                        }
                    })

                    //CODE FOR NEGATIVE(NO) BUTTON: -
                    .setNegativeButton("Ok", (dialog, which) -> {
                        //ACTION FOR "NO" BUTTON: -
                        dialog.cancel();

                    });

            //CREATING A DIALOG-BOX: -
            AlertDialog alertDialog = builder.create();
            //SET TITLE MAUALLY: -
            alertDialog.setTitle("Estimated Price");
            alertDialog.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
