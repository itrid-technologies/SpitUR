package split.com.app.ui.main.view.member_chat;

import static split.com.app.utils.Configration.CHAT_MSG_NOTIFICATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import split.com.app.databinding.FragmentMemberChatBinding;
import split.com.app.ui.main.adapter.chat.ChatAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.chat_viewmodel.ChatMemberViewModel;
import split.com.app.ui.main.viewmodel.otp_request_viewmodel.OtpRequestViewModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class MemberChat extends Fragment {

    private static final String TAG = "MemberChat";
    FragmentMemberChatBinding binding;
    String group_id, receiver_id, id;
    ChatMemberViewModel viewModel;
    private ArrayList<Object> msgs;
    ChatAdapter adapter;
    OtpRequestViewModel otpRequestViewModel;
    boolean otp_status;

    private BroadcastReceiver mChatMsgReceiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberChatBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.mcrToolbar.title.setText("Chat Member");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerMsgReceiver();

        msgs = new ArrayList<>();

        clickListeners();

        if (getArguments() != null) {
            group_id = getArguments().getString("groupId");
            receiver_id = getArguments().getString("receiverId");

            otp_status = getArguments().getBoolean("ask_otp");
        }

        if (otp_status){
            binding.askOtp.setVisibility(View.VISIBLE);
        }else {
            binding.askOtp.setVisibility(View.GONE);
        }

        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        id = pm.getData(Split.getAppContext(), "Id");

        initChatList();

    }

    private void initChatList() {
        viewModel = new ChatMemberViewModel(group_id, receiver_id, "");
        viewModel.initGetAllMessage();
        viewModel.getMessages_data().observe(getViewLifecycleOwner(), getMemberMessagesModel -> {
            if (getMemberMessagesModel.isStatus()) {
                if (getMemberMessagesModel.getMessages().size() > 0) {
                    for (int i = 0; i <= getMemberMessagesModel.getMessages().size() - 1; i++) {

                        if (String.valueOf(getMemberMessagesModel.getMessages().get(i).getSenderId()).equalsIgnoreCase(id)) {
                            msgs.add(new SenderModel(getMemberMessagesModel.getMessages().get(i).getBody(),
                                    getMemberMessagesModel.getMessages().get(i).getCreatedAt()
                            ));
                        } else {
                            msgs.add(new ReceiverModel(getMemberMessagesModel.getMessages().get(i).getBody(),
                                    getMemberMessagesModel.getMessages().get(i).getCreatedAt(),
                                    getMemberMessagesModel.getMessages().get(i).getReceiver()));
                        }
                    }
                    buildChatRv(msgs);
                }
            }
        });
    }

    private void registerMsgReceiver() {

        mChatMsgReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(CHAT_MSG_NOTIFICATION)) {
                    // chat event occurred
                    Log.e(TAG, "onReceive: chat reloaded");
                    initChatList();
                }
            }
        };
    }

    private void buildChatRv(ArrayList<Object> msgs) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.memberChatRv.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(Split.getAppContext(), msgs);
        binding.memberChatRv.setAdapter(adapter);
        binding.memberChatRv.scrollToPosition(msgs.size() - 1);

    }

    private void clickListeners() {
        binding.mcrToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendMemberMessage.setOnClickListener(view -> {
            String message = binding.messgae.getText().toString().trim();
            if (!message.isEmpty()) {
                viewModel = new ChatMemberViewModel(group_id, receiver_id, message);
                viewModel.initSendMessage();
                viewModel.getData().observe(getViewLifecycleOwner(), messageSendModel -> {
                    if (messageSendModel.isStatus()) {

                        msgs.add(new SenderModel(message, Calendar.getInstance().getTime().toString()));
                        binding.memberChatRv.scrollToPosition(msgs.size() - 1);
                        adapter.notifyItemInserted(msgs.size() - 1);
                        adapter.notifyDataSetChanged();
                        binding.messgae.setText("");
                    }
                });
            }
        });

        binding.askOtp.setOnClickListener(view -> {
            otpRequestViewModel = new OtpRequestViewModel(group_id);
            otpRequestViewModel.init();
            otpRequestViewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
                if (basicModel.isStatus()) {
                    Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mChatMsgReceiver,
                new IntentFilter(CHAT_MSG_NOTIFICATION));

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mChatMsgReceiver);
        super.onPause();
    }
}