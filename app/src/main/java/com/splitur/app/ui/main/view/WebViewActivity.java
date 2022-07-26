package com.splitur.app.ui.main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topBar.back.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent data = getIntent();
        if (data != null) {

            final String title = data.getStringExtra("title");
            binding.topBar.title.setText(title);

            final String url = data.getStringExtra("url");
            //load web view
            loadWebView(url);
        }
    }

    private void loadWebView(String url) {

        binding.webview.getSettings().setLoadsImagesAutomatically(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webview.loadUrl(url);

        binding.webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //visible
                binding.progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                //gone
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
}