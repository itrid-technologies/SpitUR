package split.com.app.ui.main.view.refund_complete;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import split.com.app.R;
import split.com.app.databinding.FragmentRefundCompletionBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Split;


public class RefundCompletion extends Fragment {

  FragmentRefundCompletionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRefundCompletionBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.completeRefundToolbar.title.setText("Refund Completed");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.completeRefundToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnHome.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.home2);
        });

        Glide.with(Split.getAppContext()).load(R.drawable.success_gif).into(binding.refundSuccessGif);

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.home2);
            }
        });

    }
}