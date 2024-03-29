package com.splitur.app.ui.main.adapter.group_member;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.splitur.app.R;
import com.splitur.app.data.model.group_member.DataItem;
import com.splitur.app.utils.Constants;


public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> {

    private List<com.splitur.app.data.model.group_member.DataItem> list;
    private final Context context;
    private ItemClickListener mListener;
    String adminId;


    public void setOnMembersActionListener(ItemClickListener listener) {
        mListener = listener;
    }


    public GroupMemberAdapter(Context appContext, List<DataItem> membersList) {
        this.context = appContext;
        this.list = membersList;
    }


    @NonNull
    @Override
    public GroupMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_list_design, parent, false);
        return new GroupMemberAdapter.ViewHolder(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull GroupMemberAdapter.ViewHolder holder, int position) {

        try {

        com.splitur.app.data.model.group_member.DataItem current_item = list.get(position);

        if (current_item.getUser() != null) {
            holder.layout.setVisibility(View.VISIBLE);

            holder.image.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(current_item.getUser().getAvatar())));

//            Glide.with(context)
//                    .load(Constants.IMG_PATH + current_item.getUser().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(holder.image);

            holder.name.setText(current_item.getUser().getUserId());


            String coins_added = Constants.coinsDate(current_item.getCreatedAt());
            holder.date.setText(coins_added);
        }else {
            holder.layout.setVisibility(View.GONE);
        }

        }catch (NullPointerException e){
            Log.e("group_member", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onRemove(int position);
        void onChat(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public ImageView remove, admin;
        public TextView name, date , chat;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            image = itemView.findViewById(R.id.member_image);
            remove = itemView.findViewById(R.id.delete_member);
            name = itemView.findViewById(R.id.member_name);
            date = itemView.findViewById(R.id.tv_coins_date);
            chat = itemView.findViewById(R.id.chat_button);
            admin = itemView.findViewById(R.id.adminBg);
            layout = itemView.findViewById(R.id.membersLayoutView);

            remove.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onRemove(getAdapterPosition());
                    }
                }
            });

            chat.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onChat(getAdapterPosition());
                    }
                }
            });


        }


    }

    public void removeAt(int position){
            list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }
}
