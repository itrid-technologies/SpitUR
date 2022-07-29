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

import com.google.gson.Gson;
import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ChatApiService;
import com.splitur.app.data.api.ChatwootApiManager;
import com.splitur.app.data.model.ChatWootAccountIdModel;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.data.model.settings.SettingsResponse;
import com.splitur.app.databinding.FragmentContactUsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.support_chat.SupportReceiverModel;
import com.splitur.app.ui.main.viewmodel.chatwoot_viewmodel.SupportChatViewModel;
import com.splitur.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactUs extends Fragment {


    FragmentContactUsBinding binding;
    String msgs;


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

        if (Constants.conversation_id == 0) {
            SupportChatViewModel viewModel = new SupportChatViewModel(0, "");
            viewModel.init();
            viewModel.getData().observe(getViewLifecycleOwner(), conversationModel -> {
                if (conversationModel.getId() != 0) {
                    Constants.conversation_id = conversationModel.getId();
                }
            });
        }

    }




    private void getSupportChat(int conversation_id) {
        Call<MessagesModel> call = ChatwootApiManager.getRestApiService().getSupportChat(Constants.ChatApiKey,conversation_id);
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<MessagesModel> call, @NonNull Response<MessagesModel> response) {
                if (response.body() != null) {
                    MessagesModel messagesModel = response.body();
                    if (messagesModel.getPayload() != null){

                        Gson gson = new Gson();
                        msgs = gson.toJson(messagesModel);
                        if (Constants.AccountId != 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("support_chat", msgs);

                            Navigation.findNavController(requireView()).navigate(R.id.action_contactUs_to_supportChat,bundle);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessagesModel> call, @NonNull Throwable t) {
                Log.e("Support Chat Error", t.getMessage());
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
            if (Constants.conversation_id != 0){
                getSupportChat(Constants.conversation_id);
            }


        });
    }
}