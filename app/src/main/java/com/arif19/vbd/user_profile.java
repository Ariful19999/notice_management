package com.arif19.vbd;

import static com.arif19.vbd.public_url.PublicUrl.rootUrl;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import the correct Toolbar class
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arif19.vbd.Config.LogIn;
import com.arif19.vbd.modal.PendingPostItem;
import com.arif19.vbd.recycleview.AddGroupMemberAdapter;
import com.arif19.vbd.recycleview.NewsFeedAdapter;
import com.arif19.vbd.recycleview.PendingPostAdapter;
import com.arif19.vbd.user.UserId;
import com.arif19.vbd.user.UserName;
import com.arif19.vbd.user.UserRole;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class user_profile extends AppCompatActivity {

    private Toolbar customActionBar; // Use the correct Toolbar class
    private ImageButton backButton;

    ImageView profileImage;
    de.hdodenhof.circleimageview.CircleImageView profilePic;

    TextView work_institute;
    TextView edu_institute;
    TextView blood_group;
    TextView name;
    Button editProfile;
    Button addPost;
    TextView actionBarTitleProfile;
    ImageButton backButtonProfile;

    RecyclerView recyclerViewGroupPost;
    private com.arif19.vbd.recycleview.PendingPostAdapter PendingPostAdapter;
    private List<PendingPostItem> pendingPostItems;
    private ArrayList<String> selectedPostId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_profile);
        setContentView(R.layout.activity_user_profile);

        profileImage = findViewById(R.id.profileImage);
        profilePic = findViewById(R.id.profilePic);
        work_institute = findViewById(R.id.work_institute);
        edu_institute = findViewById(R.id.edu_institute);
        blood_group = findViewById(R.id.blood_group);
        name = findViewById(R.id.name);
        name = findViewById(R.id.name);
        editProfile = findViewById(R.id.editProfile);
        addPost = findViewById(R.id.addPost);

        /// set toolbar value
        customActionBar = findViewById(R.id.custom_action_bar_for_profile);
        actionBarTitleProfile=customActionBar.findViewById(R.id.actionBarTitleProfile);
        backButtonProfile=customActionBar.findViewById(R.id.backButtonProfile);


        backButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile.this, PostActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile.this, edit_profile.class); // Replace NextActivity with your actual activity
                startActivity(intent);
                finish();
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile.this, add_post.class); // Replace NextActivity with your actual activity
                startActivity(intent);
                finish();
            }
        });


        /// for set the group post

        recyclerViewGroupPost = findViewById(R.id.recyclerViewGroupPost);
        recyclerViewGroupPost.setLayoutManager(new LinearLayoutManager(this));

        pendingPostItems = new ArrayList<>();
        PendingPostAdapter = new PendingPostAdapter(this, pendingPostItems);
        recyclerViewGroupPost.setAdapter(PendingPostAdapter);



        PendingPostAdapter.setOnAddMemberClickListener(new AddGroupMemberAdapter.OnAddMemberClickListener() {
            @Override
            public void onAddMemberClick(String postId) {
                // Handle the button click with memberId here
                Toast.makeText(user_profile.this, "Add button clicked for postId: " + postId, Toast.LENGTH_SHORT).show();
                selectedPostId = new ArrayList<>();
                if (selectedPostId.contains(postId)) {
                    // ID exists, remove it
                    selectedPostId.remove(postId);
                } else {
                    // ID doesn't exist, add it
                    selectedPostId.add(postId);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(user_profile.this);
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                builder.setView(customLayout);

                TextView dialogTitle = customLayout.findViewById(R.id.dialogTitle);
                TextView dialogMessage = customLayout.findViewById(R.id.dialogMessage);

                dialogTitle.setText("Alert");
                dialogMessage.setText("Are You Want to Delete This Post!");

                Button btnOk = customLayout.findViewById(R.id.btnOk);
                Button btnCancel = customLayout.findViewById(R.id.btnCancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnOk.setOnClickListener(v -> {
                    // Perform actions for OK button click
                    sendDataForDeletePost(selectedPostId);
                    alertDialog.dismiss();
                });

                btnCancel.setOnClickListener(v -> alertDialog.dismiss());


            }
        });




        FindData();

    }

    private void FindData() {

        String userId=UserId.userId;

        if (userId.equals("0")) {
            return;
        }

        // Create a JSONObject to hold the data
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Define your API URL
        String apiUrl = rootUrl+"VDB/user/find_data.php";

        // Create a request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String name_s = response.getString("name");
                            String email_s = response.getString("email");
                            String edu_institute_s = response.getString("edu_institute");
                            String work_institute_s = response.getString("work_institute");
                            String blood_group_s = response.getString("blood_group");
                            String date_of_birth_s = response.getString("date_of_birth");
                            String profile_path_s = response.getString("profile_path");


                            if (status.equals("Success")) {

                                actionBarTitleProfile.setText(name_s);
                                UserName.userName=name_s;

                                work_institute.setText(work_institute_s);
                                edu_institute.setText(edu_institute_s);
                                blood_group.setText(blood_group_s);
                                name.setText(name_s);

                                /// setting image
                                String profileImageUrl = rootUrl+profile_path_s; // Replace with the actual URL

                                // Using Picasso library for image loading and caching
                                Picasso.get()
                                        .load(profileImageUrl)
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profileImage);

                                Picasso.get()
                                        .load(profileImageUrl)
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profilePic);


                                /// then finding the post
                                fetchPostData();


                            } else {
                                // Handle other status cases if needed
                                Toast.makeText(user_profile.this, "Something is Wrong!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user_profile.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    /// finding post

    private void addDataToPostPage(JSONObject item) {
        PendingPostItem item_val = new PendingPostItem();
        try {
            // Parse individual attributes from the JSON object
            String id = item.getString("id");
            String post_text = item.getString("post_text");
            String post_date = item.getString("post_date");
            String name = item.getString("name");

            String user_profile = "";
            if (!item.isNull("user_profile")) {
                user_profile = rootUrl + item.getString("user_profile");
            }

            String video_url = "";
            if (!item.isNull("video_url")) {
                video_url = rootUrl + item.getString("video_url");
            }

            // Similarly parse 'image_url' array from the JSON object
            JSONArray imageUrlArray = item.getJSONArray("image_url");
            List<String> imageUrls = new ArrayList<>();
            for (int j = 0; j < imageUrlArray.length(); j++) {
                String imageUrl = rootUrl + imageUrlArray.getString(j);
                imageUrls.add(imageUrl);
            }

            // Set the parsed data to PendingPostItem object
            item_val.setAvatarImage(user_profile);
            item_val.setReporterName(name);
            item_val.setPostText(post_text);
            item_val.setImageUrls(imageUrls);
            item_val.setVideoUrl(video_url);
            item_val.setPostId(id);


            // Add the PendingPostItem to the list
            pendingPostItems.add(item_val);

            // Notify adapter for the data change
            PendingPostAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void fetchPostData() {
        String userId = UserId.userId;

        if (userId.equals("0")) {
            return;
        }

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("userId", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String apiUrl = rootUrl + "VDB/find_user_post.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            JSONArray dataArray = response.getJSONArray("data");

                            if (status.equals("Success")) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject item = dataArray.getJSONObject(i);
                                    addDataToPostPage(item);
                                    // Access other properties as needed
                                }
                            } else {
                                Toast.makeText(user_profile.this, "No Post Found !", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user_profile.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void sendDataForDeletePost(ArrayList<String> selectedPostId) {
        String userId = UserId.userId;
        if (userId.equals("0")) {
            return;
        }


        if(selectedPostId.size()==0){
            Toast.makeText(user_profile.this, "Select Post First", Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject jsonData = new JSONObject();
        try {

            // Add the selectedMemberId list to the JSON data
            JSONArray selectedPostIdArray = new JSONArray(selectedPostId);
            jsonData.put("selectedPostId", selectedPostIdArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String apiUrl = rootUrl + "VDB/delete_post.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");


                            if (status.equals("success")) {
                                Toast.makeText(user_profile.this, message, Toast.LENGTH_LONG).show();


                                recyclerViewGroupPost.setLayoutManager(new LinearLayoutManager(user_profile.this));

                                pendingPostItems = new ArrayList<>();
                                PendingPostAdapter = new PendingPostAdapter(user_profile.this, pendingPostItems);
                                recyclerViewGroupPost.setAdapter(PendingPostAdapter);


                                fetchPostData();


                            } else {
                                Toast.makeText(user_profile.this, "Something is Wrong!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user_profile.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}