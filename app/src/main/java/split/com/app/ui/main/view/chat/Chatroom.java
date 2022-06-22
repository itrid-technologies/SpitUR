package split.com.app.ui.main.view.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        msgs = new ArrayList<>();

        if (getArguments() != null) {
            group_id = getArguments().getString("groupId");
        }

        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        id = pm.getData(Split.getAppContext(), "ID");

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
                                    getMessagesModel.getMessages().get(i).getCreatedAt()
                            ));
                        }

                    }
                    buildChatRv(msgs);
                }
            }

        });
    }

    private void buildChatRv(ArrayList<Object> msgs) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.chatRv.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(Split.getAppContext(), msgs);
        binding.chatRv.setAdapter(adapter);
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
                        adapter.notifyDataSetChanged();
                        binding.messgae.setText("");
                    }
                });
            }
        });
    }
}