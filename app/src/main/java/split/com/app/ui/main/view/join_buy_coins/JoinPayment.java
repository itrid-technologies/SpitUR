package split.com.app.ui.main.view.join_buy_coins;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinPaymentBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class JoinPayment extends Fragment {

    FragmentJoinPaymentBinding binding;
    JoinGroupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinPaymentBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jpToolbar.title.setText("Buy Coins");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.jpToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.dJoin.setOnClickListener(view1 -> {
            MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());

            String group_id  = mySharedPreferences.getData(Split.getAppContext(),"GroupID");
            if (!group_id.isEmpty()) {
                viewModel = new JoinGroupViewModel(group_id);
                viewModel.init();
                viewModel.getData().observe(getViewLifecycleOwner(), joinGroupModel -> {
                    if (joinGroupModel.isSuccess()){
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String groupDATA = gson.toJson(joinGroupModel);
                        bundle.putString("group_credentials",groupDATA);
                        Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinCheckoutComplete,bundle);

                    }
                });
            }
        });


    }
}