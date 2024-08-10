package com.arif19.vbd;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arif19.vbd.graphQlApi.ApiClient;
import com.arif19.vbd.graphQlApi.GraphQLQuery;
import com.arif19.vbd.graphQlApi.GraphQLResponse;
import com.arif19.vbd.recycleview.CountryAdapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class graphql extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphql);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter();
        recyclerView.setAdapter(adapter);

        fetchCountries();
    }

//    private void fetchCountries() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        String query = "{ countries { name currencies awsRegion phone } }";
//        //String query = "{ countries { name } }";
//        ApiClient.createService().getCountries(new GraphQLQuery(query)).enqueue(new Callback<GraphQLResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<GraphQLResponse> call, @NonNull Response<GraphQLResponse> response) {
//                progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful() && response.body() != null) {
//                    List<GraphQLResponse.Data.Country> countries = response.body().getData().getCountries();
//                    adapter.setCountries(countries);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.e("MainActivity", "Response unsuccessful or empty");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<GraphQLResponse> call, @NonNull Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Log.e("MainActivity", "API call failed: " + t.getMessage());
//            }
//        });
//    }


private void fetchCountries() {
    progressBar.setVisibility(View.VISIBLE);

    String query = "{ countries { name  code  awsRegion phone } }";
    ApiClient.createService().getCountries(new GraphQLQuery(query)).enqueue(new Callback<GraphQLResponse>() {
        @Override
        public void onResponse(@NonNull Call<GraphQLResponse> call, @NonNull Response<GraphQLResponse> response) {
            progressBar.setVisibility(View.GONE);

            if (response.isSuccessful() && response.body() != null) {
                List<GraphQLResponse.Data.Country> countries = response.body().getData().getCountries();
                adapter.setCountries(countries);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("MainActivity", "Response unsuccessful or empty");
            }
        }

        @Override
        public void onFailure(@NonNull Call<GraphQLResponse> call, @NonNull Throwable t) {
            progressBar.setVisibility(View.GONE);
            Log.e("MainActivity", "API call failed: " + t.getMessage());
        }
    });
}

}
