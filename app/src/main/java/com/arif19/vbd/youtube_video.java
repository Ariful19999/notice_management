package com.arif19.vbd;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class youtube_video extends AppCompatActivity {

    private List<String> videoIds = new ArrayList<>();
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);


        webView = findViewById(R.id.webView);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        videoIds.add("Rwe5Aw3KPHY?si=QdvG2lmKXy7yf0wk");
        videoIds.add("Rwe5Aw3KPHY?si=QdvG2lmKXy7yf0wk");
        videoIds.add("Rwe5Aw3KPHY?si=QdvG2lmKXy7yf0wk");
        videoIds.add("Rwe5Aw3KPHY?si=QdvG2lmKXy7yf0wk");


        StringBuilder html = new StringBuilder();


         // Add YouTube video iframes
        for (String videoId : videoIds) {
            html.append("<html><body>");
            html.append("<iframe width=\"100%\" height=\"200\" src=\"https://www.youtube.com/embed/")
                    .append(videoId)
                    .append("\" frameborder=\"0\" allowfullscreen></iframe>");
            html.append("</body></html>");
        }



        String htmlCode = html.toString();
        webView.loadData(htmlCode, "text/html", "utf-8");
    }
}
