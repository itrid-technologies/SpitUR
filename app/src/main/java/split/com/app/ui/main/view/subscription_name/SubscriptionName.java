package split.com.app.ui.main.view.subscription_name;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentSubscriptionNameBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class SubscriptionName extends Fragment {

    FragmentSubscriptionNameBinding binding;
    String verification_type = "auth";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubscriptionNameBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {
        binding.emailAndPass.setOnClickListener(view -> {
            binding.emailAndPass.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.otp.setBackgroundResource(R.drawable.rect_back_with_grey_stroke);
        });

        binding.otp.setOnClickListener(view -> {
            verification_type = "otp";
            binding.otp.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.emailAndPass.setBackgroundResource(R.drawable.rect_back_with_grey_stroke);
        });

        binding.emailAndPass.setOnClickListener(view -> {
            verification_type = "auth";
            binding.otp.setBackgroundResource(R.drawable.rect_back_with_grey_stroke);
            binding.emailAndPass.setBackgroundResource(R.drawable.selected_gradient_stroke);
        });

        binding.BTNSUBNEXT.setOnClickListener(view -> {
            String title = binding.edGroupTitle.getText().toString().trim();
            if (!title.isEmpty()){
                Constants.GROUP_TITLE = title;
                Constants.VALIDATION_TYPE = verification_type;

//                MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
//                preferences.saveData(Split.getAppContext(),"GROUP_TITLE",title);
//                preferences.saveData(Split.getAppContext(),"VALIDATION_TYPE",verification_type);
                Navigation.findNavController(view).navigate(R.id.action_subscriptionName_to_slots);
            }
        });

    }
}