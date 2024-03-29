package com.splitur.app.ui.main.adapter.chat;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.splitur.app.R;
import com.splitur.app.data.model.OTpModel;
import com.splitur.app.data.model.chat_receiver.ReceiverModel;
import com.splitur.app.data.model.chat_sender.SenderModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int OUTGOING_VIEW_TYPE = 1;
    private static final int INCOMING_VIEW_TYPE = 0;
    private static final int OTP_RESPONSE_VIEW_TYPE = 2;

    private Context context;
    private final ArrayList<Object> mMessageList;


    public ChatAdapter(Split appContext, ArrayList<Object> messagesItems) {
        this.context = appContext;
        this.mMessageList = messagesItems;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receicer_layout, parent, false);
            return new RecieverVH(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otp_request_message_layout, parent, false);
            return new OTPVH(view);
        } else {
            View viewSender = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_layout, parent, false);
            return new SenderVH(viewSender);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:

                try {

                RecieverVH rvh = (RecieverVH) holder;
                ReceiverModel data = (ReceiverModel) mMessageList.get(position);
                rvh.message.setText(data.getMessage());

                String rStime;
                String[] rSentTime = data.getTime().split("T");
                rStime = rSentTime[1].substring(0, Math.min(rSentTime[1].length(), 5));
                rvh.time.setText(Constants.getTime1(data.getTime()));

                if (data.getReceiver() != null){
                    ((RecieverVH) holder).imageView.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(data.getReceiver().getAvatar())));

//                    Glide.with(context).load(Constants.IMG_PATH + data.getReceiver().getAvatar()).placeholder(R.color.images_placeholder).into(((RecieverVH) holder).imageView);
                    ((RecieverVH) holder).name.setText(data.getReceiver().getName());
                    ((RecieverVH) holder).name.setVisibility(View.VISIBLE);
                    ((RecieverVH) holder).imageView.setVisibility(View.VISIBLE);
                }
                }catch (NullPointerException e){
                    Log.e("chat", e.getMessage());
                }

                break;
            case 1:

                try {

                SenderVH svh = (SenderVH) holder;
                SenderModel data2 = (SenderModel) mMessageList.get(position);
                Log.e("TAG", "onBindViewHolder: " + data2.get_message() );
                svh.senderMessage.setText(data2.get_message());
                String sSTime;
                String[] sSentTime = data2.getTime().split("T");
                sSTime = sSentTime[1].substring(0, Math.min(sSentTime[1].length(), 5));
               // svh.time.setText(sSTime);
                }catch (NullPointerException e){
                    Log.e("chat", e.getMessage());
                }
                break;

            case 2:

                try {

                OTPVH otpvh = (OTPVH) holder;
                OTpModel tpModel = (OTpModel) mMessageList.get(position);
                Log.e("TAG", "onBindViewHolder: " + tpModel.get_message() );
                otpvh.senderMessage.setText(tpModel.get_message());

                }catch (NullPointerException e){
                    Log.e("chat", e.getMessage());
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = mMessageList.get(position);

        if (obj instanceof ReceiverModel) {
            return INCOMING_VIEW_TYPE;
        } else if (obj instanceof OTpModel) {
            return OTP_RESPONSE_VIEW_TYPE;
        }else {
            return OUTGOING_VIEW_TYPE;
        }
    }

    public static class RecieverVH extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView time, name;
        CircleImageView imageView;

        public RecieverVH(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_receiver_message);
            time = itemView.findViewById(R.id.tv_date);
            name = itemView.findViewById(R.id.chat_userName);
            imageView = itemView.findViewById(R.id.user_icons);

        }
    }



    public static class SenderVH extends RecyclerView.ViewHolder {
        private final TextView senderMessage;
      //  private final TextView time;

        public SenderVH(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.tv_sender_message);
          //  time = itemView.findViewById(R.id.tv_date);
        }
    }

    public static class OTPVH extends RecyclerView.ViewHolder {
        private final TextView senderMessage;
        //  private final TextView time;

        public OTPVH(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.otp_message);
            //  time = itemView.findViewById(R.id.tv_date);
        }
    }

}
