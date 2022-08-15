package com.splitur.app.ui.main.view.contact_us;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.JsonObject;
import com.splitur.app.R;
import com.splitur.app.data.api.ChatwootApiManager;
import com.splitur.app.data.model.chatwoot_model.ConversationModel;
import com.splitur.app.databinding.FragmentContactUsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

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

        try {

            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
            String conversation_id = sharedPreferences.getData(Split.getAppContext(), "unique_conversation_id");
            if (conversation_id.isEmpty()) {

//                SupportChatViewModel viewModel = new SupportChatViewModel(0, "");
//                viewModel.init();
//                viewModel.getData().observe(getViewLifecycleOwner(), conversationModel -> {
//                    if (conversationModel.getId() != 0) {
//                        Toast.makeText(requireContext(), conversationModel.toString(), Toast.LENGTH_SHORT).show();
//                        Constants.conversation_id = conversationModel.getId();
//                    }
//                });

                MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
                String source = preferences.getData(Split.getAppContext(), "source_id");
                String contact = preferences.getData(Split.getAppContext(), "contact_id");

                int inbox = Constants.InboxId;
                String api_key = Constants.ChatApiKey;
                int account_id = Constants.AccountId;

                JsonObject object = new JsonObject();
                object.addProperty("source_id", source);
                object.addProperty("inbox_id", inbox);
                object.addProperty("contact_id", Integer.valueOf(contact));

                Call<ConversationModel> call = ChatwootApiManager.getRestApiService().createConversation(api_key, account_id, object);
                call.enqueue(new Callback<ConversationModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ConversationModel> call, @NonNull Response<ConversationModel> response) {
                        if (response.body() != null) {
                            ConversationModel conversationModel = response.body();
                            MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
                            mySharedPreferences.saveData(Split.getAppContext(), "unique_conversation_id", String.valueOf(conversationModel.getId()));

                        } else if (response.code() == 400) {
                            if (response.errorBody() != null) {
                                Constants.getApiError(Split.getAppContext(), response.errorBody());
                            }
                        } else if (response.code() == 500) {
                            if (response.errorBody() != null) {
                                Constants.getApiError(Split.getAppContext(), response.errorBody());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ConversationModel> call, @NonNull Throwable t) {
                        Log.e("Conversation Error", t.getMessage());
                    }
                });


            }
        } catch (IllegalStateException e) {
            Log.e("Chatwoot", e.getMessage());
        }


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


            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
            String conversation_id = sharedPreferences.getData(Split.getAppContext(), "unique_conversation_id");
            if (!conversation_id.isEmpty()) {
                if (Integer.parseInt(conversation_id) != 0) {

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFromChat", true);
                    navigateToNext(view,bundle);
                }
            }
        });
    }

    private void navigateToNext(View view, Bundle bundle) {
        Navigation.findNavController(view).navigate(R.id.action_contactUs_to_createdAndJoinedGroups, bundle);

    }
}