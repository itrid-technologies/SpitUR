package com.splitur.app.ui.main.view.chat;

import static com.splitur.app.utils.Configration.GROUP_CHAT_MSG_NOTIFICATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.chat_receiver.ReceiverModel;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.data.model.send_message.MessageSendModel;
import com.splitur.app.databinding.FragmentChatroomBinding;
import com.splitur.app.ui.main.adapter.chat.ChatAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.member_chat.MemberChatAdapter;
import com.splitur.app.ui.main.viewmodel.chat_viewmodel.ChatViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Chatroom extends Fragment {


    FragmentChatroomBinding binding;
    ChatViewModel chatViewModel;
    String group_id;
    private ArrayList<Object> msgs;
    ChatAdapter adapter;
    String id;

    private BroadcastReceiver mGroupChatReceiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatroomBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.crToolbar.title.setText("Chat Group");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cliclKisteners();

        registerGroupChatReceiver();

        if (getArguments() != null) {
            group_id = getArguments().getString("groupId");
        }

        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        id = pm.getData(Split.getAppContext(), "Id");

        initChatList();

    }

    private void initChatList() {
        chatViewModel = new ChatViewModel(group_id, "");
        chatViewModel.initGetAllMessage();
        chatViewModel.getMessages_data().observe(getViewLifecycleOwner(), getMessagesModel -> {
            if (getMessagesModel.isStatus()) {

                msgs = new ArrayList<>();

                LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
                binding.chatRv.setLayoutManager(layoutManager);

                if (getMessagesModel.getMessages().size() > 0) {
                    for (int i = 0; i <= getMessagesModel.getMessages().size() - 1; i++) {

                        if (String.valueOf(getMessagesModel.getMessages().get(i).getSenderId()).equalsIgnoreCase(id)) {
                            msgs.add(new SenderModel(getMessagesModel.getMessages().get(i).getBody(),
                                    getMessagesModel.getMessages().get(i).getCreatedAt()
                            ));
                        } else {
                            msgs.add(new ReceiverModel(getMessagesModel.getMessages().get(i).getBody(),
                                    getMessagesModel.getMessages().get(i).getCreatedAt(),
                                    getMessagesModel.getMessages().get(i).getGroupSender()
                            ));
                        }
                    }
                    buildChatRv(msgs);
                }
            }
        });
    }

    private void registerGroupChatReceiver() {
        mGroupChatReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(GROUP_CHAT_MSG_NOTIFICATION)) {
                    // group chat event occurred
                    Log.e("Group Chat", "onReceive: reloaded");
                    initChatList();
                }
            }
        };
    }

    private void buildChatRv(ArrayList<Object> msgs) {

        adapter = new ChatAdapter(Split.getAppContext(), msgs);
        binding.chatRv.setAdapter(adapter);
        binding.chatRv.scrollToPosition(msgs.size() - 1);

    }

    private void cliclKisteners() {
        binding.crToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendGroupMessage.setOnClickListener(view -> {

            String message = binding.messgae.getText().toString().trim();
            if (!message.isEmpty()) {

                sendMessage(message);

            }
        });
    }

    private void sendMessage(String message) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("body", message);
        object.addProperty("group_id", group_id);


        Call<MessageSendModel> call = ApiManager.getRestApiService().sendGroupMessage("Bearer " + token, object);
        call.enqueue(new Callback<MessageSendModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageSendModel> call, @NonNull Response<MessageSendModel> response) {
                if (response.body() != null) {
                    MessageSendModel messageSendModel = response.body();
                    if (messageSendModel.isStatus()) {
                        msgs.add(new SenderModel(message, Calendar.getInstance().getTime().toString()));

                        if (msgs.size() == 1){
                            adapter = new ChatAdapter(Split.getAppContext(), msgs);
                            binding.chatRv.setAdapter(adapter);
                            binding.chatRv.scrollToPosition(msgs.size() - 1);
                            binding.messgae.setText("");
                        }else {
                            binding.chatRv.scrollToPosition(msgs.size() - 1);
                            adapter.notifyItemInserted(msgs.size() - 1);
                            adapter.notifyDataSetChanged();
                            binding.messgae.setText("");
                        }
                    }
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageSendModel> call, @NonNull Throwable t) {
                Log.e(" Error", t.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mGroupChatReceiver,
                new IntentFilter(GROUP_CHAT_MSG_NOTIFICATION));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mGroupChatReceiver);
        super.onPause();
    }
}