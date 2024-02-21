package com.arif19.vbd;

import static com.arif19.vbd.public_url.PublicUrl.rootUrl;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arif19.vbd.Config.LogIn;
import com.arif19.vbd.user.UserId;
import com.arif19.vbd.user.UserName;
import com.arif19.vbd.user.UserRole;

import org.json.JSONException;
import org.json.JSONObject;

public class forgot_pass extends AppCompatActivity {

    Button login;
    EditText userEmail;
    private ProgressDialog sending;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_login);
        setContentView(R.layout.activity_forgot_pass);


        userEmail=(EditText)findViewById(R.id.userEmail);
        login=(Button)findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataBack();
            }
        });
    }

    private void sendDataBack() {
        String s_email = userEmail.getText().toString().trim();

        if (s_email.equals("")) {
            Toast.makeText(this, "Please Fill Up Email Field", Toast.LENGTH_LONG).show();
            return;
        }

        sending = ProgressDialog.show(this, "Please wait...", "", false, false);

        // Create a JSONObject to hold the data
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("email", s_email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Define your API URL
        String apiUrl =rootUrl + "VDB/email/sent_pass_by_email.php";

        // Create a request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sending.dismiss();
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");

                            Toast.makeText(forgot_pass.this, message, Toast.LENGTH_LONG).show();

                            if (status.equals("Success")) {

                                Intent intent = new Intent(forgot_pass.this, LoginActivity.class);
                                startActivity(intent);

                                finish(); // Optional: finish the current activity if you don't want to come back to it

                            } else {

                                Toast.makeText(forgot_pass.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sending.dismiss();
                        Toast.makeText(forgot_pass.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}