package com.arif19.vbd;

import static com.arif19.vbd.public_url.PublicUrl.rootUrl;
import static com.arif19.vbd.user.UserName.userName;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arif19.vbd.device.LogOut;
import com.arif19.vbd.modal.AllCommentModal;
import com.arif19.vbd.modal.NewsFeedItem;
import com.arif19.vbd.notification.NotificationUtil;
import com.arif19.vbd.recycleview.AddGroupMemberAdapter;
import com.arif19.vbd.recycleview.AllCommentAdapter;
import com.arif19.vbd.recycleview.NewsFeedAdapter;
import com.arif19.vbd.user.UserId;
import com.arif19.vbd.user.UserName;
import com.arif19.vbd.user.UserRole;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



public class unique_post extends AppCompatActivity {

    private RecyclerView recyclerViewPost;
    private RecyclerView recyclerViewComment;
    private NewsFeedAdapter newsFeedAdapter;
    private AllCommentAdapter allCommentAdapter;
    private List<NewsFeedItem> newsFeedItems;
    private List<AllCommentModal> allCommentModal;
    private Toolbar customActionBar; // Use the correct Toolbar class
    ImageButton backButtonProfile;
    TextView actionBarTitleProfile;
    EditText commentEditText;
    ImageView sendCommentBtn;
    int  postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_profile);
        setContentView(R.layout.activity_unique_post);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("postId")) {
             postId = intent.getIntExtra("postId",-1);
//            Toast.makeText(unique_post.this, "Something is Wrong! "+postId, Toast.LENGTH_LONG).show();
        }




        recyclerViewPost = findViewById(R.id.recyclerViewPost);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(this));

        newsFeedItems = new ArrayList<>();
        newsFeedAdapter = new NewsFeedAdapter(this, newsFeedItems);
        recyclerViewPost.setAdapter(newsFeedAdapter);

        recyclerViewComment = findViewById(R.id.recyclerViewComment);

        allCommentModal = new ArrayList<>();
        allCommentAdapter = new AllCommentAdapter(this, allCommentModal);
        recyclerViewComment.setAdapter(allCommentAdapter);


        // Fetch data from the API
        fetchPostData(1);
        fetchPostComment();


        // Add scroll listener to RecyclerView
        LinearLayoutManager layoutManagerPost = new LinearLayoutManager(this);
        recyclerViewPost.setLayoutManager(layoutManagerPost);

        // Add scroll listener to RecyclerView
        LinearLayoutManager layoutManagerComment = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(layoutManagerComment);



        /// set toolbar value
        customActionBar = findViewById(R.id.custom_action_bar_for_profile);

        backButtonProfile=customActionBar.findViewById(R.id.backButtonProfile);
        actionBarTitleProfile=customActionBar.findViewById(R.id.actionBarTitleProfile);
        actionBarTitleProfile.setText("Unique Post");

        backButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(unique_post.this, PostActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        sendCommentBtn = findViewById(R.id.sendCommentBtn);
        commentEditText = findViewById(R.id.commentEditText);

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment(commentEditText.getText().toString());
            }
        });


    }








    private void addDataToPostPage(JSONObject item) {
        NewsFeedItem item_val = new NewsFeedItem();
        try {
            // Parse individual attributes from the JSON object
            int id = Integer.parseInt(item.getString("id"));
            String post_text = item.getString("post_text");
            String post_date = item.getString("post_date");
            String name = item.getString("name");
            int total_like = Integer.parseInt(item.getString("total_like"));
            int post_like = Integer.parseInt(item.getString("post_like"));
            boolean post_like_up;
            if(post_like==1){
                post_like_up=true;
            }else {
                post_like_up=false;
            }

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

            // Set the parsed data to NewsFeedItem object
            item_val.setAvatarImage(user_profile);
            item_val.setReporterName(name);
            item_val.setPostText(post_text);
            item_val.setImageUrls(imageUrls);
            item_val.setVideoUrl(video_url);
            item_val.setPostDate(post_date);

            //// for like and dislike
            item_val.setActiveLike(post_like_up);
            item_val.setPostId(id);
            item_val.setLikeCount(total_like);

            // Add the NewsFeedItem to the list
            newsFeedItems.add(item_val);

            // Notify adapter for the data change
            newsFeedAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addDataToCommentPage(JSONObject item) {
        AllCommentModal item_val = new AllCommentModal();
        try {
            // Parse individual attributes from the JSON object

            String comment = item.getString("comment");
            String name = item.getString("name");

            String user_profile = "";
            if (!item.isNull("img_url")) {
                user_profile = rootUrl + item.getString("img_url");
            }



            // Set the parsed data to NewsFeedItem object
            item_val.setCommentorProfile(user_profile);
            item_val.setCommentorName(name);
            item_val.setComment(comment);


            // Add the NewsFeedItem to the list
            allCommentModal.add(item_val);

            // Notify adapter for the data change
            allCommentAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addManualComment(String name,String comment) {
        AllCommentModal item_val = new AllCommentModal();
      //  item_val.setCommentorProfile(user_profile);
        item_val.setCommentorName(name);
        item_val.setComment(comment);

        // Add the NewsFeedItem to the list
        allCommentModal.add(item_val);

        // Notify adapter for the data change
        allCommentAdapter.notifyDataSetChanged();
    }


    /// finding post
    private void fetchPostData(int actionRole) {
        String userId = UserId.userId;

        if (userId.equals("0")) {
            return;
        }

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("userId", userId);
            jsonData.put("postId", postId);
            jsonData.put("start_limit", 0);
            jsonData.put("no_of_row", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String apiUrl = rootUrl + "VDB/find_post.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            JSONArray dataArray = response.getJSONArray("data");

                            if (status.equals("Success")) {

                                if(actionRole==1){
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject item = dataArray.getJSONObject(i);
                                        addDataToPostPage(item);
                                        // Access other properties as needed
                                    }
                                }

                            } else {
                                Toast.makeText(unique_post.this, "Something is Wrong!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(unique_post.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void fetchPostComment() {
        String userId = UserId.userId;

        if (userId.equals("0")) {
            return;
        }
        if (postId<=0) {
            return;
        }

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("user_id", userId);
            jsonData.put("post_id", postId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String apiUrl = rootUrl + "VDB/user/find_post_comment.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            JSONArray dataArray = response.getJSONArray("data");

                            if (status.equals("Success")) {

                             //   Toast.makeText(unique_post.this, "Data Found", Toast.LENGTH_LONG).show();

                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject item = dataArray.getJSONObject(i);
                                    addDataToCommentPage(item);
                                    // Access other properties as needed
                                }


                            } else {
                                Toast.makeText(unique_post.this, "Something is Wrong!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                           // Toast.makeText(unique_post.this, , Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(unique_post.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void addComment(String comment) {
        String userId = UserId.userId;

        if (userId.equals("0")) {
            return;
        }
        if (postId<=0) {
            return;
        }
        if (comment.equals("")) {
            return;
        }

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("user_id", userId);
            jsonData.put("post_id", postId);
            jsonData.put("post_comment", comment);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String apiUrl = rootUrl + "VDB/user/add_comment.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String msg = response.getString("message");

                            if (status.equals("success")) {
                                commentEditText.setText("");
                                showModalDialog( msg);

                                addManualComment(UserName.userName, comment);
                               // Toast.makeText(unique_post.this, "Comment Added Successfully", Toast.LENGTH_LONG).show();

                            } else if(status.equals("error")) {
                                Toast.makeText(unique_post.this, status, Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(unique_post.this, "Something is Wrong", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(unique_post.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void showModalDialog(String msg) {
        // Create an AlertDialog.Builder and set the message and title
        AlertDialog.Builder builder = new AlertDialog.Builder(unique_post.this);
        builder.setTitle("Success");
        builder.setMessage(msg);


        // Set positive and negative buttons for actions
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Action for OK button
            dialog.dismiss(); // Dismiss the modal
        });

        builder.setNegativeButton("Dismiss", (dialog, which) -> {
            // Action for Cancel button
            dialog.dismiss(); // Dismiss the modal
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
