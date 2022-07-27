package com.splitur.app.ui.main.view.contact_us;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.ChatWootAccountIdModel;
import com.splitur.app.data.model.settings.SettingsResponse;
import com.splitur.app.databinding.FragmentContactUsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactUs extends Fragment {


    FragmentContactUsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactUsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.contactToolbar.title.setText("Contact Us");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
        getAccountId();
    }

    private void getAccountId() {
        Call<ChatWootAccountIdModel> call = ApiManager.getRestApiService().getAccountId();
        call.enqueue(new Callback<ChatWootAccountIdModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatWootAccountIdModel> call, @NonNull Response<ChatWootAccountIdModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatWootAccountIdModel accountIdModel = response.body();
                    Constants.AccountId = Integer.parseInt(accountIdModel.getChatWootAccountId());
//                    Constants.ChatApiKey = accountIdModel.getChat_api_key();
//                    Constants.InboxId = Integer.parseInt(accountIdModel.getInbox_id());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatWootAccountIdModel> call, @NonNull Throwable t) {
                Log.e("Account Id", "onFailure: ", t);
            }
        });
    }

    private void initClickListeners() {

        binding.contactToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.contactLayout.faqLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_contactUs_to_FAQ);
        });
//
//        binding.contactLayout.chatLayout.setOnClickListener(view -> {
//            //to group created
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("isFromChat", true);
//            Navigation.findNavController(view).navigate(R.id.action_contactUs_to_createdAndJoinedGroups, bundle);
//        });


        binding.contactLayout.chatLayout.setOnClickListener(view -> {
            if (Constants.AccountId != 0) {
                Navigation.findNavController(view).navigate(R.id.action_contactUs_to_supportChat);
            }
        });
    }
}