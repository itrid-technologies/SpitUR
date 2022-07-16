package split.com.app.ui.main.view.chat;

import static split.com.app.utils.Configration.GROUP_CHAT_MSG_NOTIFICATION;

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

import java.util.ArrayList;
import java.util.Calendar;

import split.com.app.data.model.chat_receiver.ReceiverModel;
import split.com.app.data.model.chat_sender.SenderModel;
import split.com.app.databinding.FragmentChatroomBinding;
import split.com.app.ui.main.adapter.chat.ChatAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.chat_viewmodel.ChatViewModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


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
        msgs = new ArrayList<>();

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.chatRv.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(Split.getAppContext(), msgs);
        binding.chatRv.setAdapter(adapter);
        binding.chatRv.scrollToPosition(msgs.size() - 1);

    }

    private void cliclKisteners() {
        binding.crToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendMessage.setOnClickListener(view -> {
            String message = binding.messgae.getText().toString().trim();
            if (!message.isEmpty()) {
                chatViewModel = new ChatViewModel(group_id, message);
                chatViewModel.initSendMessage();
                chatViewModel.getData().observe(getViewLifecycleOwner(), messageSendModel -> {
                    if (messageSendModel.isStatus()) {
                        msgs.add(new SenderModel(message, Calendar.getInstance().getTime().toString()));
                        binding.chatRv.scrollToPosition(msgs.size() - 1);
                        adapter.notifyItemInserted(msgs.size() - 1);
                        adapter.notifyDataSetChanged();
                        binding.messgae.setText("");
                    }
                });
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