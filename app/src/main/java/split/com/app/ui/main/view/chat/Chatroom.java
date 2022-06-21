package split.com.app.ui.main.view.chat;

import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.NonUiContext;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import split.com.app.R;
import split.com.app.data.model.chat_receiver.ReceiverModel;
import split.com.app.data.model.receive_message.MessagesItem;
import split.com.app.databinding.FragmentChatroomBinding;
import split.com.app.ui.main.adapter.chat.ChatAdapter;
import split.com.app.ui.main.adapter.group_detail_adapter.GroupDetailAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.chat_viewmodel.ChatViewModel;
import split.com.app.utils.Split;


public class Chatroom extends Fragment {


    FragmentChatroomBinding binding;
    ChatViewModel chatViewModel;
    String receiver_id;
    List<MessagesItem> messagesItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatroomBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.crToolbar.title.setText("Chat member");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cliclKisteners();
        messagesItems = new ArrayList<>();

        if (getArguments() != null){
            receiver_id = getArguments().getString("receiver_id");
        }

        chatViewModel = new ChatViewModel(receiver_id,"");
        chatViewModel.initGetAllMessage();
        chatViewModel.getMessages_data().observe(getViewLifecycleOwner(), getMessagesModel -> {
            if (getMessagesModel.isStatus()){
                if (getMessagesModel.getMessages().size() > 0) {
//                    for (int i=0; i<= getMessagesModel.getMessages().size()-1; i++){
//                        if (getMessagesModel.getMessages().get(i).getReceiver() != null){
//                            messagesItems.add(new ReceiverModel(getMessagesModel.getMessages().get(i).getReceiver().getId(),
//                                    response.body().getData().getMessages().get(i).getDateAdded()
//                            ));
//                        }
//                        messagesItems.add(getMessagesModel.getMessages().get(i).i)
//
//                    }
//                }
                    messagesItems.addAll(getMessagesModel.getMessages());
                    buildChatRv();
                }
            }
        });
    }

    private void buildChatRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.chatRv.setLayoutManager(layoutManager);
        ChatAdapter adapter = new ChatAdapter(Split.getAppContext(), messagesItems);
        binding.chatRv.setAdapter(adapter);
    }

    private void cliclKisteners() {
        binding.crToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendMessage.setOnClickListener(view -> {
            String message = binding.messgae.getText().toString().trim();
            if (!message.isEmpty()){
                chatViewModel = new ChatViewModel(receiver_id,message);
                chatViewModel.initSendMessage();
                chatViewModel.getData().observe(getViewLifecycleOwner(), messageSendModel -> {
                    if (messageSendModel.isStatus()){

                    }
                });
            }
        });
    }
}