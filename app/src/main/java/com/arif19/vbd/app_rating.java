package com.arif19.vbd;

import static com.arif19.vbd.public_url.PublicUrl.rootUrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arif19.vbd.user.UserId;

import org.json.JSONException;
import org.json.JSONObject;

public class app_rating extends AppCompatActivity {

    private RatingBar ratingBar;
    private AppCompatButton rate_btn;

    private ProgressDialog sending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_rating);

        ratingBar = findViewById(R.id.ratingBar);
        rate_btn = findViewById(R.id.rate_btn);
        findUserRating();

        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataBack();
            }
        });
    }

    private void findUserRating() {

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("user_id", UserId.userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Define your API URL
        String apiUrl = rootUrl+"VDB/find_app_rate.php";

        // Create a request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonResponse =response;
                        try {
                            String status = jsonResponse.getString("status");
                            float rate = (float) jsonResponse.getDouble("rate");

                            if(status.equals("success")){
//                                Toast.makeText(app_rating.this, message, Toast.LENGTH_LONG).show();
                                ratingBar.setRating(rate);
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(app_rating.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
                        }

//                        Intent intent = new Intent(add_group.this, PostActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(app_rating.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

                        finish();
                    }
                });

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void sendDataBack() {

        float rating = ratingBar.getRating();

        if (rating<=(float) 0.0) {
            Toast.makeText(this, "Please Select Rating Star", Toast.LENGTH_LONG).show();
            return;
        }

        sending = ProgressDialog.show(this, "Please wait...", "", false, false);

        // Create a JSONObject to hold the data
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("rating", rating);
            jsonData.put("user_id", UserId.userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(app_rating.this,"Tnx For Rating "+ String.valueOf(rating), Toast.LENGTH_LONG).show();


        // Define your API URL
        String apiUrl = rootUrl+"VDB/add_app_rate.php";

        // Create a request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sending.dismiss();

                        JSONObject jsonResponse =response;
                       try {
                           String status = jsonResponse.getString("status");
                           String message = jsonResponse.getString("message");

                           if(status.equals("success")){
                               Toast.makeText(app_rating.this, message, Toast.LENGTH_LONG).show();
                           }

                       }catch (JSONException e) {
                           e.printStackTrace();
                           Toast.makeText(app_rating.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
                       }

//                        Intent intent = new Intent(add_group.this, PostActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       Toast.makeText(app_rating.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        sending.dismiss();
                        finish();
                    }
                });

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}