package com.splitur.app.ui.main.view.support_chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.data.model.chat_sender.SenderModel;
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

        viewModel = new SupportChatViewModel(0, "");
        viewModel.getChat();
        viewModel.getChatData().observe(getViewLifecycleOwner(),messagesModel -> {
            if (messagesModel.getPayload() != null){
                for (int i = 0; i<= messagesModel.getPayload().size()-1; i++) {

                    if (messagesModel.getPayload().get(i).getSender() != null) {

                        if (messagesModel.getPayload().get(i).getSender().getType().equalsIgnoreCase("contact")) {
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
        });

        if (Constants.conversation_id == 0) {
            viewModel = new SupportChatViewModel(0, "");
            viewModel.init();
            viewModel.getData().observe(getViewLifecycleOwner(), conversationModel -> {
                if (conversationModel.getId() != 0) {
                    Constants.conversation_id = conversationModel.getId();
                }
            });
        }

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
                viewModel = new SupportChatViewModel(Constants.conversation_id,query);
                viewModel.initQuery();
                viewModel.getSend_data().observe(getViewLifecycleOwner(),sendSupportMessageModel -> {
                    if (sendSupportMessageModel.getSender() != null){
                        msgs.add(new SenderModel(sendSupportMessageModel.getContent(), Calendar.getInstance().getTime().toString()));
                        binding.supportChatRv.scrollToPosition(msgs.size() - 1);
                        adapter.notifyItemInserted(msgs.size() - 1);
                        adapter.notifyDataSetChanged();
                        binding.message.setText("");
                    }
                });
            }
        });


    }
}