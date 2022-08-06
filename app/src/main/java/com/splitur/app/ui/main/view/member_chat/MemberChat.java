package com.splitur.app.ui.main.view.member_chat;

import static com.splitur.app.utils.Configration.CHAT_MSG_NOTIFICATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.splitur.app.data.model.OTpModel;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.databinding.FragmentMemberChatBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.chat_viewmodel.ChatMemberViewModel;
import com.splitur.app.ui.main.viewmodel.otp_request_viewmodel.OtpRequestViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MemberChat extends Fragment {

    private static final String TAG = "MemberChat";
    FragmentMemberChatBinding binding;
    String group_id, receiver_id, id;
    ChatMemberViewModel viewModel;
    private ArrayList<Object> msgs;
    MemberChatAdapter adapter;
    OtpRequestViewModel otpRequestViewModel;
    boolean otp_status;

    private CountDownTimer waitTimer;
    long remaining_millis;
    int i = 0;
    String timeLeftFormatted;


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


        clickListeners();

        if (getArguments() != null) {
            group_id = getArguments().getString("groupId");
            receiver_id = getArguments().getString("receiverId");

            otp_status = getArguments().getBoolean("ask_otp");
        }


        binding.memberChatRv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom <= oldBottom) {

                    binding.memberChatRv.smoothScrollToPosition(bottom);
                }
            }
        });
        if (otp_status) {
            binding.askOtp.setVisibility(View.VISIBLE);
        } else {
            binding.askOtp.setVisibility(View.GONE);
        }

//        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
//        id = pm.getData(Split.getAppContext(), "Id");
        id = Constants.ID;

        initChatList();

    }

    private void initChatList() {
        viewModel = new ChatMemberViewModel(group_id, receiver_id, "");
        viewModel.initGetAllMessage();
        viewModel.getMessages_data().observe(getViewLifecycleOwner(), getMemberMessagesModel -> {
            if (getMemberMessagesModel.isStatus()) {

                msgs = new ArrayList<>();

                LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
//                layoutManager.setStackFromEnd(true);
                binding.memberChatRv.setLayoutManager(layoutManager);

                if (getMemberMessagesModel.getMessages().size() > 0) {


                    for (int i = 0; i <= getMemberMessagesModel.getMessages().size() - 1; i++) {

                        if (getMemberMessagesModel.getMessages().get(i).isOtpMessage() && String.valueOf(getMemberMessagesModel.getMessages().get(i).getSenderId()).equalsIgnoreCase(id)){

                         msgs.add(new OTpModel(getMemberMessagesModel.getMessages().get(i).getBody(),
                                        getMemberMessagesModel.getMessages().get(i).getCreatedAt()
                                ));

                        }else {

                            if (String.valueOf(getMemberMessagesModel.getMessages().get(i).getSenderId()).equalsIgnoreCase(id)){

                                msgs.add(new SenderModel(getMemberMessagesModel.getMessages().get(i).getBody(),
                                        getMemberMessagesModel.getMessages().get(i).getCreatedAt()
                                ));
                            } else if (String.valueOf(getMemberMessagesModel.getMessages().get(i).getReceiverId()).equalsIgnoreCase(id)) {

                                msgs.add(new MemberReceiverModel(getMemberMessagesModel.getMessages().get(i).getBody(),
                                        getMemberMessagesModel.getMessages().get(i).getCreatedAt(),
                                        getMemberMessagesModel.getMessages().get(i).getSender()));
                            }else {

                            }
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

        adapter = new MemberChatAdapter(Split.getAppContext(), msgs);
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

                        if (msgs.size() == 1) {
                            adapter = new MemberChatAdapter(Split.getAppContext(), msgs);
                            binding.memberChatRv.setAdapter(adapter);
                            binding.memberChatRv.scrollToPosition(msgs.size() - 1);
                            binding.messgae.setText("");
                        } else {
                            binding.memberChatRv.scrollToPosition(msgs.size() - 1);
                            adapter.notifyItemInserted(msgs.size() - 1);
                            adapter.notifyDataSetChanged();
                            binding.messgae.setText("");
                        }
                        initChatList();

                    }
                });
            }
        });

        binding.askOtp.setOnClickListener(view -> {
            binding.askOtp.setEnabled(false);
            binding.askOtp.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5E6272")));
            otpRequestViewModel = new OtpRequestViewModel(group_id);
            otpRequestViewModel.init();
            otpRequestViewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
//                if (basicModel.isStatus()) {
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                initChatList();

                threeMinuteTimerStart();
//                }
            });


        });
    }

    private void threeMinuteTimerStart() {
        waitTimer = new CountDownTimer(180000, 1000) {//180000
            @Override
            public void onTick(long millisUntilFinished) {

                remaining_millis = millisUntilFinished;
                Log.v("Log_tag", "Tick of Progress" + i + remaining_millis);
                i++;

                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                binding.tvTimer.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                i++;
                binding.askOtp.setEnabled(true);
                binding.tvTimer.setText("ASK OTP");
                binding.askOtp.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            }
        };
        waitTimer.start();
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