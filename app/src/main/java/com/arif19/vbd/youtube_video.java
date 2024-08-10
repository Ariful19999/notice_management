package com.arif19.vbd;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

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

        // Enable JavaScript for the WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Add YouTube video IDs to the list (ensure they are clean without query parameters)
        videoIds.add("Rwe5Aw3KPHY");
        videoIds.add("Rwe5Aw3KPHY");
        videoIds.add("Rwe5Aw3KPHY");
        videoIds.add("Rwe5Aw3KPHY");

        // Build the HTML content
        StringBuilder html = new StringBuilder();

        // Start of HTML document
        html.append("<html><head>");
        html.append("<style>");
        // Define CSS class for video containers with border and bottom margin
        html.append(".video-container { border: 1px solid black; margin-bottom: 15px; }");
        html.append("</style>");
        html.append("</head><body>");

        // Loop through each video ID and create an iframe wrapped in a styled div
        for (String videoId : videoIds) {
            html.append("<div class=\"video-container\">");
            html.append("<iframe width=\"100%\" height=\"200\" src=\"https://www.youtube.com/embed/")
                    .append(videoId)
                    .append("\" frameborder=\"0\" allowfullscreen></iframe>");
            html.append("</div>");
        }

        // End of HTML document
        html.append("</body></html>");

        // Load the constructed HTML content into the WebView
        String htmlCode = html.toString();
        webView.loadData(htmlCode, "text/html", "utf-8");
    }
}
