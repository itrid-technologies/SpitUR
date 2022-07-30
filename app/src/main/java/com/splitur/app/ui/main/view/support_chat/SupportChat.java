package com.splitur.app.ui.main.view.support_chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.data.model.plans.PlanModel;
import com.splitur.app.databinding.FragmentSupportChatBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.member_chat.MemberChatAdapter;
import com.splitur.app.ui.main.viewmodel.chatwoot_viewmodel.SupportChatViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.Calendar;


public class SupportChat extends Fragment {


    FragmentSupportChatBinding binding;
    SupportChatViewModel viewModel;
    private ArrayList<Object> msgs;


    SupportChatAdapter adapter;
    String msgList;
    MessagesModel messagesModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = com.splitur.app.databinding.FragmentSupportChatBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.scToolbar.title.setText("Chat Support");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickEvents();
        msgs = new ArrayList<>();

        if (getArguments() != null){
            String data  = getArguments().getString("support_chat");
            Gson gson = new Gson();
            messagesModel = gson.fromJson(data, MessagesModel.class);
            if (messagesModel.getPayload() != null){
                buildSupportRv(messagesModel);
            }
        }

//        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getSupportChat();
//            }
//        }, 5000);






    }


    private void getSupportChat() {
        viewModel = new SupportChatViewModel(0, "");
        viewModel.getChat();
        viewModel.getChatData().observe(getViewLifecycleOwner(),messagesModel -> {
            if (messagesModel.getPayload() != null){
                msgs = new ArrayList<>();
               buildSupportRv(messagesModel);
            }
        });
    }


    private void buildSupportRv(MessagesModel messagesModel) {
        for (int i = 0; i<= messagesModel.getPayload().size()-1; i++) {

            if (messagesModel.getPayload().get(i).getSender() != null) {

                if (messagesModel.getPayload().get(i).getSender().getType().equalsIgnoreCase("user")) {
                    msgs.add(new SenderModel(messagesModel.getPayload().get(i).getContent(),
                            ""
                    ));
                } else {
                    msgs.add(new SupportReceiverModel(messagesModel.getPayload().get(i).getContent(),
                            "", messagesModel.getMeta().getAssignee()
                    ));
                }
            }
        }
        buildRec(msgs);
    }

    private void buildRec(ArrayList<Object> msgs) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.supportChatRv.setLayoutManager(layoutManager);
        adapter = new SupportChatAdapter(Split.getAppContext(), msgs);
        binding.supportChatRv.setAdapter(adapter);
        binding.supportChatRv.scrollToPosition(msgs.size() - 1);
    }

    private void clickEvents() {

        binding.scToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendSupportMessage.setOnClickListener(view -> {
            String query = binding.message.getText().toString();
            if (!query.isEmpty()){
                if (Constants.conversation_id != 0) {
                    viewModel = new SupportChatViewModel(Constants.conversation_id, query);
                    viewModel.initQuery();
                    viewModel.getSend_data().observe(getViewLifecycleOwner(), sendSupportMessageModel -> {
                        if (sendSupportMessageModel.getSender() != null) {
                            msgs.add(new SenderModel(sendSupportMessageModel.getContent(), Calendar.getInstance().getTime().toString()));
                            binding.supportChatRv.scrollToPosition(msgs.size() - 1);
                            adapter.notifyItemInserted(msgs.size() - 1);
                            adapter.notifyDataSetChanged();
                            binding.message.setText("");
                        }
                    });
                }
            }
        });


    }
}