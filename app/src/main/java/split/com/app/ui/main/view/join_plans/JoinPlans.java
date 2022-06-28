package split.com.app.ui.main.view.join_plans;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinPlansBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class JoinPlans extends Fragment {


    String url;
    FragmentJoinPlansBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinPlansBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.joinPlansToolbar.title.setText("Plans");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.joinPlansToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        if (getArguments() != null){
            url = getArguments().getString("plan_url");
        }

        WebSettings webSettings = binding.plansWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.plansWebview.loadUrl(url);
    }
}