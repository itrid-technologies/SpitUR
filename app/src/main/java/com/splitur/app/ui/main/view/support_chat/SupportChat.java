package com.splitur.app.ui.main.view.support_chat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.splitur.app.data.api.ChatwootApiManager;
import com.splitur.app.data.model.SupportActionModel;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.databinding.FragmentSupportChatBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.chatwoot_viewmodel.SupportChatViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupportChat extends Fragment {


    FragmentSupportChatBinding binding;
    SupportChatViewModel viewModel;
    private ArrayList<Object> msgs;


    SupportChatAdapter adapter;
    String msgList;
    MessagesModel messagesModel;
    String groupID, userID;
    private CountDownTimer waitTimer;
    long remaining_millis;


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

        if (getArguments() != null) {
            String data = getArguments().getString("support_chat");

            userID = getArguments().getString("chat_user_id");
            groupID = getArguments().getString("chat_group_id");
            Gson gson = new Gson();
            messagesModel = gson.fromJson(data, MessagesModel.class);
            if (messagesModel != null) {
                if (messagesModel.getPayload() != null) {
                    buildSupportRv(messagesModel);
                }
            }
        }

        binding.supportChatRv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom <= oldBottom) {

                    binding.supportChatRv.smoothScrollToPosition(bottom);
                }
            }
        });

        countDownStart();

//        Timer myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                getSupportChat();
//            }
//        }, 2000);

    }

    private void countDownStart() {
        waitTimer = new CountDownTimer((long) 3.6e+6, 2000) {//1 hour
            @Override
            public void onTick(long millisUntilFinished) {

                remaining_millis = millisUntilFinished;

                getSupportChat();
            }

            @Override
            public void onFinish() {


            }
        };
        waitTimer.start();
    }


    private void getSupportChat() {
        MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
        String conversation_id = sharedPreferences.getData(Split.getAppContext(), "unique_conversation_id");

        int account_id = Constants.AccountId;
        Call<MessagesModel> call = ChatwootApiManager.getRestApiService().getSupportChat(Constants.ChatApiKey, account_id, Integer.parseInt(conversation_id));
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<MessagesModel> call, @NonNull Response<MessagesModel> response) {
                if (response.body() != null) {
                    MessagesModel messagesModel = response.body();
                    if (messagesModel.getPayload() != null) {
                        msgs = new ArrayList<>();
                        buildSupportRv(messagesModel);


                    }
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
            public void onFailure(@NonNull Call<MessagesModel> call, @NonNull Throwable t) {
                Log.e("Support Chat Error", t.getMessage());

            }
        });
        viewModel = new SupportChatViewModel(0, "");
        viewModel.getChat();
        viewModel.getChatData().observe(getViewLifecycleOwner(), messagesModel -> {
            if (messagesModel.getPayload() != null) {

            }
        });
    }


    private void buildSupportRv(MessagesModel messagesModel) {
        for (int i = 0; i <= messagesModel.getPayload().size() - 1; i++) {

            if (messagesModel.getPayload().get(i).getSender() != null) {

                if (messagesModel.getPayload().get(i).getSender().getType().equalsIgnoreCase("user")) {

                    msgs.add(new SupportReceiverModel(messagesModel.getPayload().get(i).getContent(),
                            messagesModel.getPayload().get(i).getCreatedAt(), messagesModel.getMeta().getAssignee()
                    ));

                } else {
                    msgs.add(new SenderModel(messagesModel.getPayload().get(i).getContent(),
                            ""
                    ));
                }
            } else {
                if (!messagesModel.getPayload().get(i).getContent().isEmpty()) {
                    msgs.add(new SupportActionModel(messagesModel.getPayload().get(i).getContent(), ""));
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
            if (!query.isEmpty()) {

                binding.sendSupportMessage.setEnabled(false);
                if (msgs.size() == 0) {
                    MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
                    String conversation_id = sharedPreferences.getData(Split.getAppContext(), "unique_conversation_id");

                    if (Integer.parseInt(conversation_id) != 0) {
                        String query1;
                        if (groupID != null){
                           query1 = "My GroupID is " + groupID;
                        }else {
                            query1 = "My ID is " + userID;

                        }
                        viewModel = new SupportChatViewModel(Integer.parseInt(conversation_id), query1);
                        viewModel.initQuery();
                        viewModel.getSend_data().observe(getViewLifecycleOwner(), sendSupportMessageModel -> {
                            if (sendSupportMessageModel.getSender() != null) {
                                msgs.add(new SenderModel(sendSupportMessageModel.getContent(), Calendar.getInstance().getTime().toString()));
                                binding.supportChatRv.scrollToPosition(msgs.size() - 1);
                                adapter.notifyItemInserted(msgs.size() - 1);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

                MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
                String conversation_id = sharedPreferences.getData(Split.getAppContext(), "unique_conversation_id");

                if (Integer.parseInt(conversation_id) != 0) {
                    viewModel = new SupportChatViewModel(Integer.parseInt(conversation_id), query);
                    viewModel.initQuery();
                    viewModel.getSend_data().observe(getViewLifecycleOwner(), sendSupportMessageModel -> {
                        if (sendSupportMessageModel.getSender() != null) {
                            msgs.add(new SenderModel(sendSupportMessageModel.getContent(), Calendar.getInstance().getTime().toString()));
                            binding.supportChatRv.scrollToPosition(msgs.size() - 1);
                            adapter.notifyItemInserted(msgs.size() - 1);
                            adapter.notifyDataSetChanged();
                            binding.message.setText("");
                            binding.sendSupportMessage.setEnabled(true);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        waitTimer.cancel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        waitTimer.cancel();

    }
}