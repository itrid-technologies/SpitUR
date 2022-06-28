package split.com.app.ui.main.view.join_checkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.data.model.group_detail.DataItem;
import split.com.app.databinding.FragmentJoinCheckOutBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class JoinCheckOut extends Fragment {

   FragmentJoinCheckOutBinding binding;
    DataItem dataItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckOutBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.jcToolbar.title.setText("Join");
        binding.profile2.count.setText("143 coins");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            String data = getArguments().getString("groupDetail");

            Gson gson = new Gson();
            dataItem = gson.fromJson(data, DataItem.class);

            setData(dataItem);
        }

        initClickListeners();


    }

    private void setData(DataItem dataItem) {
        try {
            binding.profile2.netflix.setText(dataItem.getTitle());
            Glide.with(requireContext())
                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
                    .placeholder(R.color.blue)
                    .into(binding.profile2.userImage);
            binding.profile2.userName.setText(String.valueOf(dataItem.getGroupAdmin().getUserId()));
            String coin = String.valueOf(dataItem.getCostPerMember());
            Double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(((coinFloat * 30) / 100) + coinFloat);
            binding.profile2.count.setText(value + " Coins");
            binding.priceValue.setText(value + " Coins");
            binding.payment.setText(String.valueOf(dataItem.getCostPerMember()));
            binding.btnJoin.setText("Join for "+value + " Coins");

            MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
            mySharedPreferences.saveData(Split.getAppContext(),"GroupID", String.valueOf(dataItem.getId()));

        }catch (NullPointerException e){

        }



    }

    private void initClickListeners() {
        binding.btnJoin.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_joinCheckOut_to_joinTerms);
        });

        binding.jcToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }
}