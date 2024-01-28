package com.arif19.vbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class activity_notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        if (getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("title");
            String message = getIntent().getStringExtra("message");

            titleTextView.setText(title);
            messageTextView.setText(message);
        }
    }
}