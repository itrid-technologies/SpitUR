package split.com.app.ui.main.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import split.com.app.databinding.FragmentWebViewBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;

public class WebViewFragment extends Fragment {

    private FragmentWebViewBinding binding;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWebViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Dashboard.hideNav(true);
        binding.topBar.back.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });

        if (getArguments() != null) {

            final String title = getArguments().getString("title");
            binding.topBar.title.setText(title);

            final String url = getArguments().getString("url");
            //load web view
            loadWebView(url);
        }

    }//onViewCreated

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