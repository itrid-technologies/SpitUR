package com.splitur.app.ui.main.adapter.group_detail_adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.splitur.app.R;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {

    private final List<DataItem> data;
    private final Context context;
    DataItem dataItem;

    private ItemClickListener mListener;


    public void setOnGroupSelectListener(ItemClickListener listener) {
        mListener = listener;
    }


    public GroupDetailAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public GroupDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_search_list_item_design, parent, false);
        return new GroupDetailAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDetailAdapter.ViewHolder holder, int position) {

        try {

        dataItem = data.get(position);
        holder.title.setText(dataItem.getTitle());
        if (dataItem.getGroupAdmin() != null) {

            try {
                int score_value = Integer.valueOf((int) Math.round(dataItem.getGroupAdmin().getSpliturScore()));
                if (score_value < 30) {
                    holder.user.setBorderColor(Color.parseColor("#FF3D00"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF3D00")));
                } else if (score_value < 70) {
                    holder.user.setBorderColor(Color.parseColor("#F7931A"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F7931A")));
                } else if (score_value > 70) {
                    holder.user.setBorderColor(Color.parseColor("#0FB900"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0FB900")));
                } else {

                }


            } catch (NullPointerException e) {
                e.getMessage();
            }


            holder.score.setText(String.valueOf(Math.round(dataItem.getGroupAdmin().getSpliturScore())));
            if (dataItem.getGroupAdmin().isOnlineOflineStatus()) {
                holder.online_offline.setText("Online");
                holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0FB900")));
            } else {
                holder.online_offline.setText("Offline");
                holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }
            try {
                holder.user.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(dataItem.getGroupAdmin().getAvatar())));
            } catch (NumberFormatException e) {
                Log.e("DataType Error", e.getMessage());
                holder.user.setImageResource(R.drawable.user);
            }
//            Glide.with(context)
//                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(holder.user);
            holder.rateOf_id.setText(String.format("@%s", dataItem.getGroupAdmin().getUserId()));
        }

        /*
         * Handle slots & joined members
         */

        final int memberCount = dataItem.getTotalMembers();
        final int slotsCount = dataItem.getSlots();


        for (int i = 0; i < slotsCount; i++) {
            if (i == memberCount) {
                switch (i) {
                    case 1:
                        visibleMember(holder.one, holder.two, holder.three, holder.four, holder.five, holder.six);
                        break;

                    case 2:

                        visibleMember(holder.two, holder.one, holder.three, holder.four, holder.five, holder.six);

                        break;
                    case 3:
                        visibleMember(holder.three, holder.one, holder.two, holder.four, holder.five, holder.six);

                        break;
                    case 4:
                        visibleMember(holder.four, holder.one, holder.two, holder.three, holder.five, holder.six);

                        break;
                    case 5:
                        visibleMember(holder.five, holder.one, holder.two, holder.four, holder.three, holder.six);

                        break;
                    case 6:
                        visibleMember(holder.six, holder.one, holder.two, holder.four, holder.five, holder.three);
                        break;
                }
            }
        }//for

        try {
            for (int i = slotsCount + 1; i <= 6; i++) {
                switch (i) {
                    case 1:
                        holder.one.setVisibility(View.GONE);
                        break;
                    case 2:
                        holder.two.setVisibility(View.GONE);
                        break;
                    case 3:
                        holder.three.setVisibility(View.GONE);
                        break;
                    case 4:
                        holder.four.setVisibility(View.GONE);
                        break;
                    case 5:
                        holder.five.setVisibility(View.GONE);
                        break;
                    case 6:
                        holder.six.setVisibility(View.GONE);
                        break;
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        holder.group_id.setText(String.valueOf(dataItem.getGroupId()));

        if (dataItem.getSubCategory() != null) {
            holder.rate_icon.setImageResource(
                    Constants.getCategoryIcon(context, dataItem.getSubCategory().getCategory().getId()));
        }
        String id = Constants.ID;
        if (dataItem.getGroupAdmin().getId() == Integer.parseInt(id)) {
            String coin = String.valueOf(dataItem.getCostPerMember());
            holder.coins.setText(String.format("%s %s", coin, context.getString(R.string.inr_sign)));
        } else {
            String coin = String.valueOf(dataItem.getCostPerMember());
            double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            holder.coins.setText(String.format("%s %s", value, context.getString(R.string.inr_sign)));
        }

        }catch (NullPointerException e){
            Log.e("group_detail", e.getMessage());
        }
    }

    private void visibleMember(TextView one, TextView two, TextView three, TextView four, TextView five, TextView six) {

        one.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
        one.setTextColor(Color.WHITE);
        one.setAlpha(1);

        two.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        two.setAlpha((float) 0.4);
        three.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        three.setAlpha((float) 0.4);
        four.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        four.setAlpha((float) 0.4);
        five.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        five.setAlpha((float) 0.4);
        six.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        six.setAlpha((float) 0.4);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener {
        void onGroupSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, online_offline, group_id, rateOf_id, one, two, three, four, five, six, score, coins;
        ImageView online_icon, rate_icon;
        CircleImageView user;
        LinearLayout llSlots;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.tv_username);
            online_offline = itemView.findViewById(R.id.tv_online);
            group_id = itemView.findViewById(R.id.tv_groupid_value);
            rateOf_id = itemView.findViewById(R.id.tv_rateOf);
            one = itemView.findViewById(R.id.tv_one);
            two = itemView.findViewById(R.id.tv_two);
            three = itemView.findViewById(R.id.tv_three);
            four = itemView.findViewById(R.id.tv_four);
            five = itemView.findViewById(R.id.tv_five);
            six = itemView.findViewById(R.id.tv_six);
            llSlots = itemView.findViewById(R.id.slots);
            online_icon = itemView.findViewById(R.id.online_icon);
            rate_icon = itemView.findViewById(R.id.icon);
            score = itemView.findViewById(R.id.tv_score);
            user = itemView.findViewById(R.id.iv_user);
            coins = itemView.findViewById(R.id.coins_value);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onGroupSelect(getAdapterPosition());
                    }
                }
            });


        }
    }
}
