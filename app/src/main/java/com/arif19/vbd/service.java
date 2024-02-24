package com.arif19.vbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arif19.vbd.adapter.service_adapter;
import com.arif19.vbd.modal.service_modal;

import java.util.ArrayList;

public class service extends AppCompatActivity {
    GridView coursesGV;
    private Toolbar customActionBar; // Use the correct Toolbar class
    ImageButton backButtonProfile;
    TextView actionBarTitleProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_profile);
        setContentView(R.layout.activity_service);

        /// for back  page
        customActionBar = findViewById(R.id.custom_action_bar_for_profile);
        actionBarTitleProfile=customActionBar.findViewById(R.id.actionBarTitleProfile);
        backButtonProfile=customActionBar.findViewById(R.id.backButtonProfile);
        actionBarTitleProfile.setText("Notice Management");

        backButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(service.this, MainActivity.class);
                startActivity(intent);
                 finish();
            }
        });



        coursesGV = findViewById(R.id.idGVcourses);
        ArrayList<service_modal> grid_block = new ArrayList<service_modal>();

        grid_block.add(new service_modal("User", R.drawable.single_avatar));
        grid_block.add(new service_modal("Potato Disease Detection", R.drawable.detection));
        grid_block.add(new service_modal("Google Map", R.drawable.map));
        grid_block.add(new service_modal("Others", R.drawable.group_avatar));
        service_adapter adapter = new service_adapter(this, grid_block);
        coursesGV.setAdapter(adapter);

        coursesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                service_modal selectedItem = grid_block.get(position);
                String itemName = selectedItem.getTitle();

                if(itemName.equals("User")){
                    Intent intent = new Intent(service.this, PostActivity.class);
                    startActivity(intent);
                }

                if(itemName.equals("Google Map")){
                    Intent intent = new Intent(service.this, google_map.class);
                    startActivity(intent);
                }


            }
        });
    }
}