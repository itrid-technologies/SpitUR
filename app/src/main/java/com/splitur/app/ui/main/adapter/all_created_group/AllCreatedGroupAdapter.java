package com.splitur.app.ui.main.adapter.all_created_group;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.splitur.app.R;
import com.splitur.app.data.model.all_created_groupx.DataItem;
import com.splitur.app.utils.Constants;

public class AllCreatedGroupAdapter extends RecyclerView.Adapter<AllCreatedGroupAdapter.GroupVH> {

    private final List<DataItem> dataItems;
    private final Context context;
    private final boolean supportChat;

    private AllCreatedGroupAdapter.ItemClickListener mListener;


    public void setOnCreatedGroupClickListener(AllCreatedGroupAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public AllCreatedGroupAdapter(Context appContext, List<DataItem> list, boolean shouldGoToSupportChat) {
        this.context = appContext;
        this.dataItems = list;
        this.supportChat = shouldGoToSupportChat;
    }

    @NonNull
    @Override
    public AllCreatedGroupAdapter.GroupVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_created_group_design, parent, false);
        return new AllCreatedGroupAdapter.GroupVH(view,mListener,supportChat);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCreatedGroupAdapter.GroupVH holder, int position) {

        try {

        DataItem current_item =  dataItems.get(position);
        holder.name.setText(current_item.getGroupTitle());

        int space = current_item.getSlots() - current_item.getTotalMembers();

        if (space == 0){
            holder.slots.setText("Full");
            holder.slots.setTextColor(Color.parseColor("#0FB900"));

        }else {
            holder.slots.setText((String.valueOf(space)) + " Slots Free");
            holder.slots.setTextColor(Color.parseColor("#FFDD72"));
        }


        if (!current_item.isStatus()){
            holder.open.setText("Closed");
            holder.open.setBackgroundResource(R.drawable.closed_bg);
        }
        if (current_item.isMessage_seen()){
            holder.messages_status.setVisibility(View.GONE);
        }else {
            holder.messages_status.setVisibility(View.VISIBLE);
        }

        holder.icon.setImageResource(
                Constants.getCategoryIcon(context,current_item.getSubCategory().getCategory().getId()));

        }catch (NullPointerException e){
            Log.e("all_created_group", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public interface ItemClickListener{
        void onCategorySelect(int position);
    }

    public static class GroupVH extends RecyclerView.ViewHolder {
        public ImageView icon, messages_status;
        public TextView name,slots, open;

        public GroupVH(@NonNull View itemView, ItemClickListener mListener, boolean supportChat) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.group_title);
            slots = itemView.findViewById(R.id.space);
            icon = itemView.findViewById(R.id.search_icons);
            open = itemView.findViewById(R.id.tv_open);
            messages_status = itemView.findViewById(R.id.chat_status);

            open.setOnClickListener(view -> {
                String isOpen = open.getText().toString().trim();
                if (supportChat) {
                    if (isOpen.equalsIgnoreCase("Closed")) {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    } else {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    }
                }else {
                    if (isOpen.equalsIgnoreCase("Closed")) {

                    } else {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                String isOpen = open.getText().toString().trim();
                if (supportChat) {
                    if (isOpen.equalsIgnoreCase("Closed")) {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    } else if (isOpen.equalsIgnoreCase("Open")) {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    } else {

                    }
                }else {
                    if (isOpen.equalsIgnoreCase("Closed")) {

                    } else if (isOpen.equalsIgnoreCase("Open")) {
                        if (mListener != null) {
                            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                mListener.onCategorySelect(getAdapterPosition());
                            }
                        }
                    } else {

                    }
                }
            });


        }
    }
}
